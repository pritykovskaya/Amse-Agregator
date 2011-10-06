<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main" match="//collection" mode="show">
        <xsl:if test="//menu-item/name = 'error'">
            К сожалению в нашем каталоге пока нет ни одного отеля.
        </xsl:if>
        <xsl:if test="//menu-item/name != 'error'">
            <xsl:for-each select="//data[@id='hotels']//menu-item">
            <a>
                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=
                    <xsl:value-of
                            select="type"/>
                </xsl:attribute>
                <xsl:value-of select="name"/>
            </a>
            <br/>
        </xsl:for-each>
        </xsl:if>

    </xsl:template>

</xsl:stylesheet>
