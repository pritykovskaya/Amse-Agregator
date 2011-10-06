<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>


    <xsl:template name="leftmenu">
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
        <xsl:call-template name="countryTopBlock"/>
        <xsl:call-template name="continentSelectBlock"/>
    </xsl:template>

    <xsl:template name="main">
        <xsl:call-template name="indexMainContent"/>
        <!--<xsl:apply-templates select="page/data/collection" mode="show"/>-->
    </xsl:template>

    <xsl:template name="rightmenu">
        <!-- @todo заменить это на параметризуемый вызов блоков  -->
        <xsl:call-template name="loginBlock"/>
        <xsl:call-template name="attractionTopBlock"/>
        <xsl:call-template name="rankingBlock"/>
    </xsl:template>

    <!--<xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>-->

    <xsl:template name="indexMainContent">
        <xsl:apply-templates select="page/data[@id='showAttractionDesc']/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="collection" mode="show">
        <div id="main-container">
            <xsl:apply-templates select="attraction" mode="attractiondescription-xml"/>
        </div>
    </xsl:template>

    <xsl:template match="attraction" mode="attractiondescription-xml">
        <xsl:if test="type = 'Error'">
            <br/>
            <div class="description">
                Информация временно недоступна.
            </div>
        </xsl:if>


        <table width="100%">
            <tr>
                <td align="left" class="title">
                    <xsl:if test="parents != ''">
                        <xsl:apply-templates select="parents/cell" mode="name"/>
                    </xsl:if>

                    <xsl:value-of select="name" disable-output-escaping="yes"/>
                </td>
            </tr>
            <tr align="left">
                <td>
                    <!-- -1 - for field name -->
                    <xsl:if test="(count(field-map/node()[text()='true'])-1) != 1">
                        <xsl:if test="field-map/description = 'true'">
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=all"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Общая информация
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/description = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=description"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Описания
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/images = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=images"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                Галерея
                            </a>
                        </xsl:if>

                        <xsl:if test="field-map/list = 'true'">
                            |
                            <a href="attractiondescription.xml?id={id}&amp;type={type}&amp;tab=list"
                               onclick="document.getElementById('main-container').contentwindow.location.reload(true) ;">
                                <xsl:if test="type = 'City'">
                                    Достопримечательности города
                                </xsl:if>
                                <xsl:if test="type = 'Country'">
                                    Города страны
                                </xsl:if>
                                <xsl:if test="type = 'Continent'">
                                    Страны континента
                                </xsl:if>
                            </a>
                        </xsl:if>                  	
                	</xsl:if>
                	<xsl:if test="//AUTH != -1 and //AUTH != 0">
                	
                	<form id="addForm" name="addForm" method="GET" action="attractiondescription.xml">
                   		<input type="hidden" name="id" value="{id}"/>
                   		<input type="hidden" name="type" value="{type}"/> 
       					<input type="hidden" name="flag" value="1"/>
       					<input type="hidden" name="user">
       					 	<xsl:attribute name="value">
       					 		<xsl:value-of select="//uid"/>
       					 	</xsl:attribute>
       					 </input>
   					</form>
		   			<a class="buttonLink" onclick="addClicked()"><FONT size = "2"> Добавить в избранное</FONT></a> | <a class="buttonLink" onclick="printClicked()"><FONT size = "2"> Печатать избранное</FONT></a>
               		 <form id="printForm" name="printForm" method="GET" action="forPrint.xml">
		       					 <input type="hidden" name="user">
		       					 	<xsl:attribute name="value">
		       					 		<xsl:value-of select="//uid"/>
		       					 	</xsl:attribute>
		       					 </input>
		   			</form> 
               		</xsl:if>
                </td>
            </tr>
            <tr>
                <td class="description" colspan="1" width="30%">
                    <xsl:if test="field-map/description = 'true'">
                        <table>
                            <tr>
                                <td onload="GDownloadUrl()" onunload="GUnload()">
                                    <!--<div id="google-map" style="width: 350px; height: 250px;"/>-->
                                    <!--<div id="map_canvas" style="width: 350px; height: 250px;"/>-->
                                    <!--<xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>-->
                                    <xsl:if test="//data[@id='maps']//record != ''">
                                        <xsl:variable name="countCoords" select="count(//data[@id='maps']//record)"/>
                                        <div id="map_canvas" style="width: 350px; height: 250px;"/>
                                    </xsl:if>
                                    <!--	<xsl:apply-templates select="//data[@id='maps']//record" mode="googlemap"/>
                                    -->
                                </td>
                                <td width="20px"></td>
                                <td>
                                    <!-- All Info -->

                                    <xsl:if test="images-array != ''">
                                        <xsl:variable name="count-of-images" select="count(images-array/string)"/>
                                        <table width="350px">
                                            <tr style="heigth: 150px">
                                                <td>

                                                    <xsl:apply-templates select="images-array/string[1]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                                <td>

                                                    <xsl:apply-templates select="images-array/string[2]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                                <td>

                                                    <xsl:apply-templates select="images-array/string[3]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                            </tr>
                                            <!-- Если меньше 4х картинок -->
                                            <xsl:if test="$count-of-images &lt; 3">
                                                <tr>
                                                    <td class="small_image"/>
                                                </tr>
                                            </xsl:if>
                                            <tr style="heigth: 150px;">
                                                <td>
                                                    <xsl:apply-templates select="images-array/string[4]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                                <td>

                                                    <xsl:apply-templates select="images-array/string[5]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                                <td>

                                                    <xsl:apply-templates select="images-array/string[6]"
                                                                         mode="attractiondescription-xml-mini"/>
                                                </td>
                                            </tr>
                                        </table>

                                    </xsl:if>
                                </td>
                            </tr>
                           
                        </table>
                    </xsl:if>

                    <!-- Description -->
                    <div id="aaa" width="400px !important;">
                        <xsl:if test="description != ''">
                            <xsl:value-of select="description" disable-output-escaping="yes"/>
                        </xsl:if>
                        <xsl:if test="description-array != ''">
                            <xsl:apply-templates select=".//description-array//string" mode="description-array"/>
                        </xsl:if>
                    </div>

                    <!-- Images -->
                    <xsl:if test="images-array != '' and description-array=''">
                        <div style="margin-left: 53px;">
                        <xsl:apply-templates select=".//images-array//string" mode="images-array"/>
                        </div>
                    </xsl:if>

                    <!-- List of attractions -->
                    <xsl:if test="attraction-list/menu-item != ''">
                        <xsl:if test="type = 'City'">
                            Достопримечательности города
                        </xsl:if>
                        <xsl:if test="type = 'Country'">
                            Города страны
                        </xsl:if>
                        <xsl:if test="type = 'Continent'">
                            Страны континента
                        </xsl:if>
                        <br/>

                        <ul>
                            <xsl:for-each select="attraction-list/menu-item">
                                <li>
                                    <a href="attractiondescription.xml?id={id}&amp;type={type}">
                                        <xsl:value-of select="name" disable-output-escaping="yes"/>
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="string" mode="images-array">
        <img src="{.}" class="big_image"/>
        <span style="padding:0px 15px;"/>
    </xsl:template>

    <xsl:template match="string" mode="description-array">
        <div>
            <xsl:value-of select="." disable-output-escaping="yes"/>
        </div>
    </xsl:template>

    <xsl:template match="string" mode="attractiondescription-xml-mini">
        <img src="{.}" class="small_image"/>
        <span style="padding:20px 0px;"/>
    </xsl:template>

    <xsl:template match="cell" mode="name">
        <xsl:choose>
            <xsl:when test="value = 'Continent'">
                <a class="title" href="attractiondescription.xml?id={preceding-sibling::cell/value}&amp;type=Continent&amp;tab=list">
                    <xsl:value-of select="preceding-sibling::cell/name"/>
                </a>
                <xsl:text> - </xsl:text>
            </xsl:when>
            <xsl:when test="value = 'Country'">
                <a class="title" href="attractiondescription.xml?id={preceding-sibling::cell/value}&amp;type=Country&amp;tab=list">
                    <xsl:value-of select="preceding-sibling::cell/name"/>
                </a>
                <xsl:text> - </xsl:text>
            </xsl:when>
            <xsl:when test="value = 'City'">
                <a class="title" href="attractiondescription.xml?id={preceding-sibling::cell/value}&amp;type=City&amp;tab=list">
                    <xsl:value-of select="preceding-sibling::cell/name"/>
                </a>
                <xsl:text> - </xsl:text>
            </xsl:when>
        </xsl:choose>

    </xsl:template>

</xsl:stylesheet>
