# RxFirebase
[![CircleCI](https://circleci.com/gh/yongjhih/rxfirebase.svg?style=shield)](https://circleci.com/gh/yongjhih/rxfirebase)
[![codecov](https://codecov.io/gh/yongjhih/rxfirebase/branch/master/graph/badge.svg)](https://codecov.io/gh/yongjhih/rxfirebase)

RxJava binding APIs for [Firebase](https://firebase.google.com/) Android SDK.

## Usage

```java
RxFirebaseRemoteConfig.fetches(() -> firebaseRemoteConfig).subscribe();
```

Kotlin:

```kt
firebaseRemoteConfig.fetches().subscribe();
```

See [official documentation](https://firebase.google.com/docs/) for the details.

## Installation

```gradle
compile 'com.yongjhih.rxfirebase:rxfirebase2-config:0.0.1'
compile 'com.yongjhih.rxfirebase:rxfirebase2-config-kotlin:0.0.1' // optional
compile 'com.yongjhih.rxfirebase:rxfirebase2-auth:0.0.1'
compile 'com.yongjhih.rxfirebase:rxfirebase2-auth-kotlin:0.0.1' // optional
compile 'com.yongjhih.rxfirebase:rxfirebase2-database:0.0.1'
compile 'com.yongjhih.rxfirebase:rxfirebase2-database-kotlin:0.0.1' // optional

// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-messaging:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-messaging-kotlin:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-crash:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-crash-kotlin:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-storage:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-storage-kotlin:10.2.1'

// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-invites:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-invites-kotlin:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-ads:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-ads-kotlin:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-appindexing:10.2.1'
// TODO: compile 'com.yongjhih.rxfirebase:rxfirebase2-appindexing-kotlin:10.2.1'
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
