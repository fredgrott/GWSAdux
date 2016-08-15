GWSAdux
=======

GWSAadux, an Android Application Architecture that is designed from the 
beginning to promote correct practices in App Responsiveness without 
necessarily having to use RxJava or Agera.

# Premise

Despite all the good things that RxJava and Agera do they do not directly 
increase App Responsiveness. But, than again we always see the sample 
apps using RxJava without using the basics encouraging App Responsiveness.

There are some other fads such as Flux and Redux, but those are somewhat 
ill-fitting for Android as they imply a single-page application but an 
Android Application is often a many-page application with needs that conflict 
with a single state store.

Hence the Android reDDux in project name as its a new take on android application 
architecture.

# Senior Android Developer Note

Java Virtual Machines do this amazing stuff but even though we have new 
GarbageCollection technology its still a pause in app responsiveness when 
a gc operation takes place on a mobile device. My Efforts are to eliminate 
the need for gc operations in certain area of concerns for an 
android application.

App Responsiveness Areas of Concern:

1. Background Tasks

2. Domain Models

3. View Models

4. Parceable Objects

5. Models in the Network Stream

If I follow the tip #1 from the EffectiveJava Book, 2nd Edition, and make 
all my models immutables and make some of the collection containers read-only 
than I can increase App Responsiveness of an Android Application without 
necessarily using RxJava or Agera. Yet, If I use RxJava or Agera I in-fact 
have to use these techniques, in the first place, in my application 
architecture to actually increase App Responsiveness.

APP ARCHITECTURE

For application architecture I revived the traditional MVVM from Microsoft 
and adopted it for the Android Environment. Thus, its no longer a traditional 
MVVM model in that I did separate out State and View-Logic into its own 
api and ViewModel into another api. 

The benefit to this are:

1. View Holder pattern slim view-controllers in the form of activities and 
   fragments.
   
2. View-State-View-Logic and View-Model are separated into their own 
   distinct apis.
   
3. The MVVM app architecture pattern is very understandable to beginning 
   android developers without sacrificing more power when you need it.
   
4. Minimal use of functional programming, its used in one place in the 
   View-State api. That way the beginning android developer is not 
   overwhelmed by a huge learning curve.

# Articles

# Tools to Use with GWSAdux

It is imperative that you convert your models to immutables using autovalue:

[AutoValue developed by Google to generate the Immutables]()

And to convert your collections containers in the network stream to read-only ones using Solid:

[Solid developed by      to create some other collections read-only containers and the backported stream api]()

# Usage

if library, describe how to download library using jitpack than describe how to use the library.

I use jitpack to upload my libraries so you put this in your root buildscript:

```groovy
allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
```
Than in the module buildscript:


```groovy
compile 'com.github.shareme:GWSAdux:{latest-release-number}@aar'
```

# Developed By

![gws logo](art/gws_github_header.png)

<a href="http://stackoverflow.com/users/237740/fred-grott">
<img src="http://stackoverflow.com/users/flair/237740.png" width="208" height="58" alt="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers" title="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers">
</a>


Created by [Fred Grott](http://shareme.github.com).


# Credits

ViewModel Module:

[AndroidViewmOdel developed by ]()

ViewState Module:

[Phlux developed by ]()


# References

[EffectiveJava, 2nd Edition by ]()



# Resources

Resources can be found at the [GWSTheWayOfAndroid wiki](http://github.com/shareme/GWSTheWayOfAndroid/wiki).



# License

Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
