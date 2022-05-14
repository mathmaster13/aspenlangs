# [aspenlangs](https://aspenlangs.neocities.org/)
A package for code related to mochaspen's conlangs.

This repo does ***not*** include any code related to Fynotek, which is mochaspen's most developed conlang.
Due to its significance, it has its own separate package (specifically for the JVM) at [fynotek-java](https://github.com/mathmaster13/fynotek-java/).

Despite this project currently solely being written in Kotlin, the JVM is the only target that this will directly compile to
(just because I added JVM annotations to keep my suffering to a minimum; you can easily remove them if you need).
Why? Because Gradle is hell, and IDEA doesn't allow Kotlin multiplatform without it.
If you want to use this project in JavaScript, be my guest—but prepare to add a lot of `@JsName` annotations to avoid Kotlin's silly mangling.