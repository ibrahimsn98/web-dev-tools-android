package me.ibrahimsn.core.presentation.view.tabView

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes

class TabParser(private val context: Context, @XmlRes res: Int) {

    private val parser: XmlResourceParser = context.resources.getXml(res)

    fun parse(): List<TabItem> {
        val items: MutableList<TabItem> = mutableListOf()
        var eventType: Int?

        do {
            eventType = parser.next()
            if (eventType == XmlResourceParser.START_TAG && parser.name == "item") {
                items.add(getTabConfig(parser))
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)

        return items
    }

    private fun getTabConfig(parser: XmlResourceParser): TabItem {
        val attributeCount = parser.attributeCount
        var itemText: String? = null

        for (index in 0 until attributeCount) {
            when (parser.getAttributeName(index)) {
                "title" -> {
                    itemText = try {
                        context.getString(parser.getAttributeResourceValue(index, 0))
                    } catch (notFoundException: Resources.NotFoundException) {
                        parser.getAttributeValue(index)
                    }
                }
            }
        }

        return TabItem(itemText ?: "")
    }
}
