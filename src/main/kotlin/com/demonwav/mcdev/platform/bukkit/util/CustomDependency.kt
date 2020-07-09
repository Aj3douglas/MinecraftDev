/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2020 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.platform.bukkit.util

enum class CustomDependency(val groupId:String, val artifactId:String, val version:String) {
    MATTS_COMMAND_LIB(
            "me.mattstudios.utils",
            "matt-framework",
    "1.3"),
    MATT_GUI_LIB(
            "me.mattstudios.utils",
            "matt-framework-gui",
            "1.2.8"
    ),
    VAULT_API(
    "com.github.MilkBowl",
    "VaultAPI",
    "1.7"
    )
}