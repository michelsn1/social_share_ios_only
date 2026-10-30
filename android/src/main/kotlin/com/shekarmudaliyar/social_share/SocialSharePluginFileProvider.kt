package com.shekarmudaliyar.social_share

import androidx.core.content.FileProvider

// Subclasse propria para nao colidir com outro FileProvider do app no merge do manifest.
class SocialSharePluginFileProvider : FileProvider()
