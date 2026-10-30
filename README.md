# social_share

Wide variety of sharing options you'll need to share directly to certain popular apps or just share with default native share.

## Introduction

Works on both platforms `Android` and `iOS`

It provides you with most of the popular sharing options
With this plugin you can share on instagram stories and facebook stories and also copy to clipboard

## Usage

### Android Configuration

Nothing to configure: the plugin's manifest declares its own `FileProvider` and the `<queries>` that Android 11+ needs to see Instagram and Facebook.

On Android only `shareInstagramStory`, `shareFacebookStory` and `checkInstalledAppsForShare` are implemented; the other methods return `notImplemented`.

### iOS Configuration

#### Add this to your `Info.plist` to use share on instagram and facebook story

```
<key>LSApplicationQueriesSchemes</key>
	<array>
	<string>instagram-stories</string>
	<string>facebook-stories</string>
	<string>facebook</string>
	<string>instagram</string>
	<string>twitter</string>
	<string>whatsapp</string>
    <string>tg</string>
	</array>
```

### Add this if you are using share on facebook. For this you have to create an app on https://developers.facebook.com/ and get the App ID

```
<key>FacebookAppID</key>
<string>xxxxxxxxxxxxxxx</string>
```

#### shareInstagramStory

```
SocialShare.shareInstagramStory(imageFile.path, "#ffffff",
                              "#000000", "https://deep-link-url");
```

#### shareInstagramStorywithBackground

```
 SocialShare.shareInstagramStorywithBackground(image.path, "https://deep-link-url",
                              backgroundImagePath: backgroundimage.path);
```

#### shareFacebookStory

For iOS

```
SocialShare.shareFacebookStory(image.path,"#ffffff","#000000",
                              "https://deep-link-url","facebook-app-id");
```

For Android
appID is mandatory if using shareFacebookStory or else it wont work

```
SocialShare.shareFacebookStory(image.path,"#ffffff","#000000",
                              "https://deep-link-url","facebook-app-id",
							  appId: "xxxxxxxxxxxxx");
```

#### copyToClipboard

```
SocialShare.copyToClipboard("This is Social Share plugin");
```

#### shareTwitter

```
//without hashtags
SocialShare.shareTwitter("This is Social Share plugin");

//with hashtags
SocialShare.shareTwitter(
                              "This is Social Share twitter example",
                              hashtags: ["hello", "world", "foo", "bar"]);

//with hashtags and link
SocialShare.shareTwitter(
                              "This is Social Share twitter example",
                              hashtags: ["hello", "world", "foo", "bar"],url:"https://your-url-here/");
```

#### shareSms

```
//without url link in message
SocialShare.shareSms("This is Social Share Sms example");

//with url link in message
SocialShare.shareSms("This is Social Share Sms example",url: "https://your-url-here/");
```

#### shareWhatsapp

```
SocialShare.shareWhatsapp("Hello World");
```

#### shareTelegram

```
SocialShare.shareTelegram("Hello World");
```

#### shareOptions

This will open the default native share options

```
//without an image
SocialShare.shareOptions("Hello world");

//with an image
SocialShare.shareOptions("Hello world",imagePath: image.path);
```

#### checkInstalledAppsForShare

```
SocialShare.checkInstalledAppsForShare();
```

# Here's few demo!

## Story share on Instagram

![insta story share demo](demogifs/insta.gif)

## Story share on Facebook

![fb story share demo](demogifs/fbshare.gif)

## Copy to Clipboard

![copy demo](demogifs/copy.gif)

# Known issues

On Android 6, Share to Instagram Stories may not open Instagram app or load the specified image.

# Buy me a :coffee: !!

((More coffee == more code) == faster updates)

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://paypal.me/b1naryishere/10)
