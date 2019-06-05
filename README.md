# Measurement Kit for Android

This repository contains Measurement Kit for Android. That is, it contains
the Java native interface (JNI) code as well as its Java counterpart.

## How to integrate a release in your Android project

### Using gradle

Add the following line to `app/build.gradle`'s `dependencies`:

```diff
 dependencies {
+  api "org.openobservatory.measurement_kit:android-libs:$version"
```

Where `$version` is the version you want to use (e.g. `0.4.3-aar-3`).

This is the approach that we follow in the [android-example](
https://github.com/measurement-kit/android-example) app.

### Manually verifying digital signature

Apparently, it's not easy to automatically verify packages signature when
downloading packages from jcenter using gradle. If you want to verify
dependencies, proceed as follows:

1. download the latest AAR and its digital signature from our
   [jcenter-hosted repository](https://dl.bintray.com/measurement-kit/android/org/openobservatory/measurement_kit/android-libs/)

2. verify the digital signature using `gpg --verify <asc-file>`

3. create the `libs` directory and move the AAR inside it

4. to the toplevel `build.gradle` add:

```diff
 allprojects {
   repositories {
     jcenter()
+    flatDir {
+      dirs 'libs'
+    }
   }
 }
```

5. to `app/build.gradle` add:

```diff
 dependencies {
+  api "org.openobservatory.measurement_kit:android-libs:$version"
```

Where `$version` is the version you have downloaded (e.g. `0.4.3-aar-3`).

## Building the distribution

We currently only support building from macOS.

### Installing dependencies

1. add our tap

```
brew tap measurement-kit/measurement-kit
```

2. install dependencies with brew

```
brew install android-measurement-kit android-sdk generic-assets
```

3. install Android-platform components

```
sdkmanager --install ndk 'build-tools;28.0.3' 'platforms;android-28'
```

### Updating dependencies

Upgrade dependencies installed using brew:

```
brew upgrade
```

Upgrade the Android platform:

```
sdkmanager --update
```

### Environment variables

```
export ANDROID_HOME=/usr/local/Caskroom/android-sdk/<version>
```

### Makefile targets

The Makefile is very short and self explanatory. By reading it, you should
be able to understand in what order the scripts in the `./script` are
called. Also the scripts are quite simple and easy to follow.

#### Preparing the repository

```
make configure
```

#### Creating the distribution

```
make dist
```

#### Signing the distribution

```
make sign
```

This is an optional step that unfortunately currently only works if you are
Simone Basso, because it his hardcoding his PGP key.

## Publishing the distribution

Once you have built the AAR and the POM files, you should upload them to
[jcenter](https://bintray.com/measurement-kit/android/android-libs).
