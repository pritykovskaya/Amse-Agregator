<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="leftmenu">
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
        <xsl:call-template name="countryTopBlock"/>
        <xsl:call-template name="continentSelectBlock"/>
    </xsl:template>
    
    <xsl:template name="rightmenu">
        <xsl:call-template name="loginBlock"/>
        <xsl:call-template name="attractionTopBlock"/>
        <xsl:call-template name="rankingBlock"/>
    </xsl:template>

    <xsl:template name="main">

        <table width="100%">
            <tr>
                <xsl:for-each select="//collection">
                    <xsl:sort order="ascending" select="menu-item[1]/name"/>
                    <td valign="top" align="left">
                        <table>
                            <tr>
                                <xsl:value-of select="menu-item[1]/name"/>
                            </tr>
                            <xsl:for-each select="menu-item">
                                <xsl:if test="position() > 1">
                                    <tr class="simpleTopItemContent">
                                        <xsl:if test="position() mod 2 = 0">
                                            <td class="allcountries-grey">
                                            <a>
                                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of
                                                        select="id"/>&amp;type=Country</xsl:attribute>
                                                <xsl:value-of select="name"/>
                                            </a>
                                            </td>
                                        </xsl:if>
                                        <xsl:if test="position() mod 2 != 0">
                                            <td class="allcountries">
                                            <a>
                                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of
                                                        select="id"/>&amp;type=Country</xsl:attribute>
                                                <xsl:value-of select="name"/>
                                            </a>
                                            </td>
                                        </xsl:if>
                                    </tr>
                                </xsl:if>
                            </xsl:for-each>
                        </table>
                        <br/>


                    </td>
                </xsl:for-each>
            </tr>
        </table>

    </xsl:template>
</xsl:stylesheet>
