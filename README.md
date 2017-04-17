# RxFirebase

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxFirebase-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5594)
[![CircleCI](https://circleci.com/gh/yongjhih/rxfirebase.svg?style=shield)](https://circleci.com/gh/yongjhih/rxfirebase)
[![codecov](https://codecov.io/gh/yongjhih/rxfirebase/branch/master/graph/badge.svg)](https://codecov.io/gh/yongjhih/rxfirebase)


![](art/rxfirebase.png)

RxJava binding APIs for [Firebase](https://firebase.google.com/) Android SDK.

## Usage

```java
RxFirebaseAuth.changes(FirebaseAuth.getInstance()).subscribe();
```

```kt
FirebaseAuth.getInstance().changes().subscribe()
```

```java
RxFirebaseRemoteConfig.fetches(firebaseRemoteConfig).subscribe();
```

```kt
firebaseRemoteConfig.fetches().subscribe();
```

```java
RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance()).subscribe();
```

```java
RxFirebaseAuth.getCurrentUser(FirebaseAuth.getInstance()).subscribe();
```

```java
RxFirebaseAuth.signInAnonymous(FirebaseAuth.getInstance()).subscribe();
```

```java
RxFirebaseUser.updateProfile(user, new UserProfileChangeRequest.Builder()
        .setDisplayName("Andrew Chen")
        .setPhotoUri(Uri.parse("https://github.com/yongjhih/rxfirebase/art/rxfirebase.png"))
        .build())
        .subscribe();
```

```java
RxFirebaseDatabase.dataChanges(ref).subscribe();
```

```java
RxFirebaseDatabase.data(ref).subscribe();
```

See [official documentation](https://firebase.google.com/docs/) for the details.

## Installation

```gradle
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-config:-SNAPSHOT'
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-config-kotlin:-SNAPSHOT' // for kotlin
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-auth:-SNAPSHOT'
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-auth-kotlin:-SNAPSHOT' // for kotlin
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-database:-SNAPSHOT'
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-database-kotlin:-SNAPSHOT' // for kotlin
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-storage:-SNAPSHOT'
compile 'com.github.yongjhih.rxfirebase:rxfirebase2-storage-kotlin:-SNAPSHOT' // for kotlin

// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-messaging:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-messaging-kotlin:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-crash:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-crash-kotlin:10.2.1'

// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-invites:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-invites-kotlin:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-ads:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-ads-kotlin:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-appindexing:10.2.1'
// TODO: compile 'com.github.yongjhih.rxfirebase:rxfirebase2-appindexing-kotlin:10.2.1'
```

## Comparison

|                           | Multiple Libs | RxJava2 | Kotlin | Unit Test | RxTasks | jitpack | auth | database | storage | config | crash  | messaging  |
|---------------------------|---------------|---------|--------|-----------|---------|---------|------|----------|---------|--------|--------|------------|
| yongjhih/rxfirebase       |       x       | x       | x      |     x     | x       | x       | x    | x        | x       | x      | *      | *          |
| nmoskalenko/RxFirebase    |       -       | -       | -      |     x     | -       | -       | x    | x        | x       | -      | -      | -          |
| kunny/RxFirebase          |       x       | x       | x      |     x     | -       | -       | x    | x        | -       | -      | -      | -          |
| ezhome/Android-RxFirebase |       -       | -       | -      |     x     | -       | -       | x    | x        | -       | -      | -      | -          |
| b3er/RxFirebase2          |       x       | x       | x      |     x     | !       | -       | x    | x        | x       | -      | -      | -          |
| ashdavies/rx-firebase     |       -       | x       | -      |     x     | x       | -       | x    | x        | -       | -      | -      | -          |
| FrangSierra/Rx2Firebase   |       -       | x       | -      |     x     | -       | x       | x    | x        | x       | -      | -      | -          |


## See Also

* https://github.com/yongjhih/rxtasks

## License

```
Copyright 2017 Andrew Chen
Copyright 2016-2017 Taeho Kim <jyte82@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
