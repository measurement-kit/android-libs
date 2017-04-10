# Measurement Kit for Android

This repository contains Measurement Kit for Android. That is, it contains
the Java native interface (JNI) code as well as its Java counterpart.

## How to integrate a release in your Android project

### Using gradle

Add the following line to `app/build.gradle`'s `dependencies`:

```diff
 dependencies {
+  compile "org.openobservatory.measurement_kit:android-libs:$version"
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

2. verify the digital signature using `gpg2 --verify <asc-file>`

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
+  compile "org.openobservatory.measurement_kit:android-libs:$version"
```

Where `$version` is the version you have downloaded (e.g. `0.4.3-aar-3`).

This is the approach that we follow in the [ooniprobe-android](
https://github.com/TheTorProject/ooniprobe-android) app.

## How to build a new release

The `Makefile` file allows you to cross-compile JNI libs suitable for being
used by Android applications for all target architectures.

To see all the available targets, just type:

```
make
```

The Makefile will verify all downloaded binaries using GnuPG. Binaries are
digitally signed by Simone Basso
(PGP key: `7388 77AA 6C82 9F26 A431 C5F4 80B6 9127 7733 D95B`) or
by Lorenzo Primiterra
(PGP key: `1191 0C85 CD8C D493 8DFA  17F7 AA09 A57A ECEB 9D12`). You can
fetch this key using gpg using the following command:

```bash
gpg --recv-keys 738877AA6C829F26A431C5F480B691277733D95B \
                11910C85CD8CD4938DFA17F7AA09A57AECEB9D12
```

After this step, to generate an AAR containing the Java files and the
corresponding compiled libraries, type:

```
make dist NDK_BUILD=/path/to/ndk-build
```

Note that you *MUST* explicitly provide the path to ndk-build on your
system. See below for more information on this point.

This command will perform the following steps:

1. download the latest prebuilt MeasurementKit dependencies from GitHub
   and verify their digital signature

2. clone MeasurementKit sources in `jni/measurement-kit`, check out a
   specific version, and generate the list of files to be compiled that
   will be passed to `ndk-build`

3. Use javah and/or SWIG to automatically generate bits of Java code
   and/or C wrappers exposing a JNI API

4. cross compile MeasurementKit (`jni/measurement-kit`) and its JNI
   wrappers (`jni/wrappers`) for all available architectures using
   the `ndk-build` command

5. package the result into an AAR (which will be located in the root
   directory of the repository) and digitally sign it

6. generate a minimal POM file and sign it.

When developing, if you want to quickly recompile and build again the
distribution without running all the above steps, do:

```
make redist NDK_BUILD=/path/to/ndk-build
```

This will only run steps 4 and 5 above and, since ndk-build uses `make`,
it will be much quicker because only changed files will be compiled.

A Unix environment is assumed (we mostly develop for Android using a
macOS Sierra system). You need to have the following executables
in your PATH:

- git
- gpg2
- javah
- wget

Most of these could be installed using your distributions package
manager (try [Homebrew](http://brew.sh/) for macOS). As regards ndk-build,
we recommend to [install it using Android studio](
https://developer.android.com/ndk/guides/index.html#download-ndk)
since in our experience this is the most reliable way to get a
working ndk-build. (This should explain why we need to pass the
`NDK_BUILD` variable explicitly to `make`: Android studio does
not install ndk-build in the `PATH`.) On macOS, Android studio installs
the ndk-build at `~/Library/Android/sdk/ndk-bundle/ndk-build`.

Once you have built the AAR and the POM files, you should upload them to
[jcenter](https://bintray.com/measurement-kit/android/android-libs). To this
end, remember to specify the path where files need to appear, which should
follow this pattern:

```
org/openobservatory/measurement_kit/android-libs/$version/
```

where `$version` is the version indicated in the `Makefile`.
