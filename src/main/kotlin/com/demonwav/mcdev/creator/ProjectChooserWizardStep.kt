/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2017 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.creator

import com.demonwav.mcdev.asset.MCMessages
import com.demonwav.mcdev.asset.PlatformAssets
import com.demonwav.mcdev.platform.PlatformType
import com.demonwav.mcdev.platform.bukkit.BukkitProjectConfiguration
import com.demonwav.mcdev.platform.bungeecord.BungeeCordProjectConfiguration
import com.demonwav.mcdev.platform.canary.CanaryProjectConfiguration
import com.demonwav.mcdev.platform.forge.ForgeProjectConfiguration
import com.demonwav.mcdev.platform.liteloader.LiteLoaderProjectConfiguration
import com.demonwav.mcdev.platform.sponge.SpongeProjectConfiguration
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ui.IdeBorderFactory
import java.awt.Desktop
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JEditorPane
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.event.HyperlinkEvent

class ProjectChooserWizardStep(private val creator: MinecraftProjectCreator) : ModuleWizardStep() {

    private lateinit var chooserPanel: JPanel
    private lateinit var panel: JPanel
    private lateinit var infoPanel: JPanel

    private lateinit var infoPane: JEditorPane
    private lateinit var spongeIcon: JLabel
    private lateinit var bukkitPluginCheckBox: JCheckBox
    private lateinit var spigotPluginCheckBox: JCheckBox
    private lateinit var paperPluginCheckBox: JCheckBox
    private lateinit var spongePluginCheckBox: JCheckBox
    private lateinit var forgeModCheckBox: JCheckBox
    private lateinit var bungeeCordPluginCheckBox: JCheckBox
    private lateinit var liteLoaderModCheckBox: JCheckBox
    private lateinit var canaryPluginCheckBox: JCheckBox
    private lateinit var neptunePluginCheckBox: JCheckBox

    override fun getComponent(): JComponent {
        chooserPanel.border = IdeBorderFactory.createBorder()
        infoPanel.border = IdeBorderFactory.createBorder()

        // HTML parsing and hyperlink support
        infoPane.contentType = "text/html"
        infoPane.addHyperlinkListener { e ->
            if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(e.url.toURI())
                }
            }
        }

        // Set types
        bukkitPluginCheckBox.addActionListener { toggle(bukkitPluginCheckBox, spigotPluginCheckBox, paperPluginCheckBox) }
        spigotPluginCheckBox.addActionListener { toggle(spigotPluginCheckBox, bukkitPluginCheckBox, paperPluginCheckBox) }
        paperPluginCheckBox.addActionListener { toggle(paperPluginCheckBox, bukkitPluginCheckBox, spigotPluginCheckBox) }
        spongePluginCheckBox.addActionListener { fillInInfoPane() }
        forgeModCheckBox.addActionListener { fillInInfoPane() }
        liteLoaderModCheckBox.addActionListener { fillInInfoPane() }
        bungeeCordPluginCheckBox.addActionListener { fillInInfoPane() }
        canaryPluginCheckBox.addActionListener { toggle(canaryPluginCheckBox, neptunePluginCheckBox) }
        neptunePluginCheckBox.addActionListener { toggle(neptunePluginCheckBox, canaryPluginCheckBox) }

        spongeIcon.icon = PlatformAssets.SPONGE_ICON_2X

        return panel
    }

    private fun toggle(one: JCheckBox, two: JCheckBox) {
        if (one.isSelected) {
            two.isSelected = false
            fillInInfoPane()
        }
    }

    private fun toggle(one: JCheckBox, two: JCheckBox, three: JCheckBox) {
        if (one.isSelected) {
            two.isSelected = false
            three.isSelected = false
            fillInInfoPane()
        }
    }

    private fun fillInInfoPane() {
        var text = "<html><font size=\"4\">"

        if (bukkitPluginCheckBox.isSelected) {
            text += bukkitInfo
            text += "<p/>"
        }

        if (spigotPluginCheckBox.isSelected) {
            text += spigotInfo
            text += "<p/>"
        }

        if (paperPluginCheckBox.isSelected) {
            text += paperInfo
            text += "<p/>"
        }

        if (spongePluginCheckBox.isSelected) {
            text += spongeInfo
            text += "<p/>"
        }

        if (forgeModCheckBox.isSelected) {
            text += forgeInfo
            text += "<p/>"
        }

        if (liteLoaderModCheckBox.isSelected) {
            text += liteLoaderInfo
            text += "<p/>"
        }

        if (bungeeCordPluginCheckBox.isSelected) {
            text += bungeeCordInfo
            text += "<p/>"
        }

        if (canaryPluginCheckBox.isSelected) {
            text += canaryInfo
            text += "<p/>"
        }

        if (neptunePluginCheckBox.isSelected) {
            text += neptuneInfo
        }

        text += "</font></html>"

        infoPane.text = text
    }

    override fun updateDataModel() {
        creator.settings.clear()

        if (bukkitPluginCheckBox.isSelected) {
            val configuration = BukkitProjectConfiguration()
            configuration.type = PlatformType.BUKKIT
            creator.settings.put(PlatformType.BUKKIT, configuration)
        }

        if (spigotPluginCheckBox.isSelected) {
            val configuration = BukkitProjectConfiguration()
            configuration.type = PlatformType.SPIGOT
            creator.settings.put(PlatformType.BUKKIT, configuration)
        }

        if (paperPluginCheckBox.isSelected) {
            val configuration = BukkitProjectConfiguration()
            configuration.type = PlatformType.PAPER
            creator.settings.put(PlatformType.BUKKIT, configuration)
        }

        if (spongePluginCheckBox.isSelected) {
            creator.settings.put(PlatformType.SPONGE, SpongeProjectConfiguration())
        }

        if (forgeModCheckBox.isSelected) {
            creator.settings.put(PlatformType.FORGE, ForgeProjectConfiguration())
        }

        if (liteLoaderModCheckBox.isSelected) {
            creator.settings.put(PlatformType.LITELOADER, LiteLoaderProjectConfiguration())
        }

        if (bungeeCordPluginCheckBox.isSelected) {
            creator.settings.put(PlatformType.BUNGEECORD, BungeeCordProjectConfiguration())
        }

        if (canaryPluginCheckBox.isSelected) {
            val configuration = CanaryProjectConfiguration()
            configuration.type = PlatformType.CANARY
            creator.settings.put(PlatformType.CANARY, configuration)
        }

        if (neptunePluginCheckBox.isSelected) {
            val configuration = CanaryProjectConfiguration()
            configuration.type = PlatformType.NEPTUNE
            creator.settings.put(PlatformType.CANARY, configuration)
        }

        creator.settings.values.iterator().next().isFirst = true
    }

    override fun validate(): Boolean {
        return bukkitPluginCheckBox.isSelected ||
            spigotPluginCheckBox.isSelected ||
            paperPluginCheckBox.isSelected ||
            spongePluginCheckBox.isSelected ||
            forgeModCheckBox.isSelected ||
            liteLoaderModCheckBox.isSelected ||
            bungeeCordPluginCheckBox.isSelected ||
            canaryPluginCheckBox.isSelected ||
            neptunePluginCheckBox.isSelected
    }

    companion object {
        private val bukkitInfo = MCMessages["setup.chooser.bukkit"]
        private val spigotInfo = MCMessages["setup.chooser.spigot"]
        private val paperInfo = MCMessages["setup.chooser.paper"]
        private val bungeeCordInfo = MCMessages["setup.chooser.bungeecord"]
        private val spongeInfo = MCMessages["setup.chooser.sponge"]
        private val forgeInfo = MCMessages["setup.chooser.forge"]
        private val liteLoaderInfo = MCMessages["setup.chooser.liteloader"]
        private val canaryInfo = MCMessages["setup.chooser.canary"]
        private val neptuneInfo = MCMessages["setup.chooser.neptune"]
    }
}
