[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidAudioConverter-green.svg?style=true)](https://android-arsenal.com/details/1/4341) [![Release](https://jitpack.io/v/adrielcafe/AndroidAudioConverter.svg)](https://jitpack.io/#adrielcafe/AndroidAudioConverter)

# AndroidAudioConverter

> Convert audio files inside your Android app easily. This is a wrapper of [mobile-ffmpeg](https://github.com/tanersener/mobile-ffmpeg) lib.

Supported formats:
* AAC
* MP3
* M4A
* WMA
* WAV
* FLAC

Lib size: ~9mb

## How To Use

* Add this permission into your `AndroidManifest.xml` and [request in Android 6.0+](https://developer.android.com/training/permissions/requesting.html)
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

* Convert audio files async
```java
AndroidAudioConverter.with(this)
    // Your current audio file
    .setFile(flacFile)  
    
    // Your desired audio format 
    .setFormat(AudioFormat.MP3)
    
    // An callback to know when conversion is finished
    .setCallback(callback)
    
    // Start conversion
    .convert();
```

## Compatible with 64bits apps
```app/build.gradle
        ndk {
            abiFilters 'x86', 'x86_64', 'armeabi-v7a', 'arm64-v8a'
        }
```

## Dependencies
* [mobile-ffmpeg](https://github.com/tanersener/mobile-ffmpeg)

## Want to RECORD AUDIO into your app?
**Take a look at [AndroidAudioRecorder](https://github.com/adrielcafe/AndroidAudioRecorder)! Example of usage [here](https://github.com/adrielcafe/AndroidAudioRecorder/issues/8#issuecomment-247311572).**

## License
```
The MIT License (MIT)

Copyright (c) 2016 Adriel Café

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
