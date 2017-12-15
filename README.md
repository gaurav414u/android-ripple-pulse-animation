# android-ripple-pulse-animation
A cool ripple and pulse background animation for android

# Dependencies

# Usage

The following properties can be used either in Layout or Programatically

- `duration` : Duration in milliseconds for the ripple animation
- `ripple_color` : ARGB color for the pulse/ripple
- `start_radius` : Starting radius of the pulse
- `end_radius` : Ending radius of the pulse
- `ripple_type` : Ripple type. Accepted values : *'fill'*, *'stroke'*(ring type)
- `stroke_width` : The width of the ring if using ripple_type = stroke

## Layout

```xml
<com.gauravbhola.ripplepulsebackground.RipplePulseLayout
          android:layout_width="200dp"
          android:layout_height="200dp"
          android:clipChildren="false"
          ripple:rippleColor="#3D66C7"
          ripple:rippleType="stroke"
          ripple:strokeWidth="2dp"
          ripple:startRadius="42dp"
          ripple:endRadius="100dp"
          ripple:duration="2000"
          android:id="@+id/layout_ripplepulse">

      <com.gauravbhola.ripplepulsebackground.sample.views.RoundedImageView
              android:layout_width="90dp"
              android:layout_height="90dp"
              android:layout_centerInParent="true"
              android:src="@drawable/contact"/>
</com.gauravbhola.ripplepulsebackground.RipplePulseLayout>
```

## Activity
```java
public class MainActivity extends AppCompatActivity {
    RipplePulseLayout mRipplePulseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);
    }
}
```
### start animation
```java
mRipplePulseLayout.startRippleAnimation();
```
### stop animation
```java
mRipplePulseLayout.stopRippleAnimation();
```

License
=======
    Copyright 2017 **Gaurav Bhola**

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.