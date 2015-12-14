# Measurement Kit for Android

This repository contains Measurement Kit for Android. That is, it contains
the Java native interface (JNI) code as well as its Java counterpart.

The `Makefile` file allows you to cross-compile JNI libs suitable for being
used by Android applications for all target architectures.

To see all the available targets, just type:

```
make
```

To generate a tarball containing the Java files and the corresponding
compiled libraries, type:

```
make dist
```

This will download the latest Measurement Kit binaries from GitHub, verify
them with GnuPG, cross-compile the JNI code and statically link it with the
downloaded binaries, package a tarball suitable for distribution.

A Unix environment is assumed. You need to have the following executables
in your PATH:

- git
- gpg2
- javah
- ndk-build
- wget

Most of these could be installed using your distributions package
manager. As regards ndk-build, [there are instructions to install it inside
Measurement Kit repository](https://github.com/measurement-kit/measurement-kit/tree/master/mobile/android#installing-the-ndk).
