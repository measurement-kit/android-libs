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

## How to build a new release

Make sure you export `ANDROID_HOME` (`$HOME/Library/Android/sdk` on
macOS with Android Studio installed) and `ANDROID_NDK_ROOT`
(`$ANDROID_HOME/ndk-bundle` on macOS with Android Studio installed).

A Unix environment is assumed. The following tools need to be
installed on your system:

- Android Studio
- ndk-build
- git
- gradle
- gpg2
- javah
- wget

We recommend installing NDK via Android Studio.

When you're all set, then

```sh
make
```

will build the `aar` and the `pom` files.

There is an optional step,

```sh
make sign
```

that unfortunately currently only works if you are Simone Basso, because
it his hardcoding his PGP key.

The Makefile is very short and self explanatory. By reading it, you should
be able to understand in what order the scripts in the `./script` are
called. Also the scripts are quite simple and easy to follow.

It is worth mentioning that we currently use [another repository](
https://github.com/measurement-kit/mkall-java) as the place we pull
the sources from. This is done because sometimes it's easier to
debug JNI issues with Java rather than using Android. However, this
is currently an implementation detail that _may_ change.

Once you have built the AAR and the POM files, you should upload them to
[jcenter](https://bintray.com/measurement-kit/android/android-libs).
