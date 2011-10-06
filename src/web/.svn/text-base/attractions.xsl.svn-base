<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:for-each select="//collection">
            <xsl:for-each select="attraction">
                <xsl:if test="type = 'Error'">
                    <br/>
                    <text-area class="description">По вашему запросу ничего не найдено. Попробуйте изменить параметры
                        поиска или воспользуйтесь нашим каталогом.
                    </text-area>
                </xsl:if>

                <!--<xsl:variable name="c">City</xsl:variable>-->
                <!--<xsl:if test="type = 'City')">-->
                <!--<text class="attraction_name_big">-->
                <!--<xsl:value-of select="type"/>-->
                <!--</text>-->
                <!--</xsl:if>-->
                <!--</xsl:if>-->

                <table>
                    <tr>
                        <td align="left">

                            <a>
                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>
                            </a>
                            <br/>


                        </td>
                    </tr>
                    <tr>
                        <xsl:if test="position() mod 2 = 0">
                            <td class="description-grey" colspan="2">
                                <xsl:if test="description != ''">
                                    <xsl:value-of select="description"/>
                                </xsl:if>
                            </td>
                        </xsl:if>
                        <xsl:if test="position() mod 2 != 0">
                            <td class="description" colspan="2">
                                <xsl:if test="description != ''">
                                    <xsl:value-of select="description"/>
                                </xsl:if>
                            </td>
                        </xsl:if>
                    </tr>

                </table>
            </xsl:for-each>
            
        </xsl:for-each>

    </xsl:template>
</xsl:stylesheet>
