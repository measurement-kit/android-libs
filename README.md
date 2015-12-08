# Measurement Kit JNI Libs

This repository contains Measurement Kit Java native interface (JNI). It
also contains the code to generate Measurement Kit JNI libs.

The `Makefile` file allows you to cross-compile JNI libs suitable for being
used by Android applications.

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

The version number of the tarball will be the version downloaded from
GitHub, plus the abbreviated reference of the current HEAD.

A Unix environment is assumed. You need to have the following executables
in your PATH:

- git
- wget
- ndk-build
- gpg2

Git, wget, and gpg2 could be installed using your distributions package
manager. As regards ndk-build, [there are instructions to install it inside
Measurement Kit repository](https://github.com/measurement-kit/measurement-kit/tree/master/mobile/android#installing-the-ndk).
