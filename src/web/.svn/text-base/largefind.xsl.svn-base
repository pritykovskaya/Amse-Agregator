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
        <script type="text/javascript">
            function searchClicked1(){
            document.searchForm1.submit();
            }
        </script>
        <form id="searchForm" name="searchForm1" method="POST" action="search.xml">
            <h1>Расширенный поиск</h1>
            <table class="header">
                <tr>
                    <td>
                    </td>
                    <td colspan="2">
                        <table id="searchContainer">
                            <tr>
                                <td id="searchForm">
                                    <input type="text" id="search" name="findTextBox1"/>
                                </td>
                                <td class="searchButtonTd">
                                    <a class="buttonLink" onclick="searchClicked1()">Найти</a>
                                </td>
                            </tr>
                        </table>

                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                    </td>

                    <td id="linksContainer" colspan="1">

                        <table id="searchTable" align="right">
                            <br/>
                            <tr class="search_checkbox">
                                <td>
                                    <input type="checkbox" name="countryCheckbox"/>
                                    Страна
                                </td>
                                <td>
                                    <input type="checkbox" name="cityCheckbox"/>
                                    Город
                                </td>
                                <td>
                                    <input type="checkbox" name="museumCheckbox"/>
                                    Музей
                                </td>
                                <td>
                                    <input type="checkbox" name="archAttractionCheckbox"/>
                                    Архитектура
                                </td>
                                <td>
                                    <input type="checkbox" name="naturalAttractionCheckbox"/>
                                    Природа
                                </td>
                                <td>

                                    <input type="checkbox" name="cafeCheckbox"/>
                                    Кафе

                                </td>

                                <td colspan="2">
                                    <input type="checkbox" name="entertainmentCheckbox"/>
                                    Развлечения
                                </td>
                                <td>
                                    <input type="checkbox" name="shoppingCheckbox"/>
                                    Шопинг
                                </td>
                                <td>
                                    <input type="checkbox" name="hotelCheckbox"/>
                                    Отель
                                </td>
                            </tr>
                            <!-- Добавить сюда вторую строку дял подчеркиваний ссылок. -->
                        </table>
                    </td>
                </tr>
            </table>
        </form>

        <!--<td width="100%" valign="top">-->
        <!--<form action="search.xml" method="post">-->
        <!--<h2>Расширенный поиск</h2>-->

        <!--<table width="100%" valign="top">-->
        <!--<tr>-->
        <!--<td colspan="2">-->
        <!--Введите название объекта для поиска:-->
        <!--</td>-->
        <!--<td valign="top" colspan="3">-->
        <!--<div>-->
        <!--<input name="findTextBox"-->
        <!--value=""-->
        <!--maxlength="40"/>-->
        <!--</div>-->
        <!--</td>-->

        <!--</tr>-->
        <!--<tr align="left" width="70%" class="checkbox">-->
        <!--<td>-->
        <!--<input type="checkbox" name="countryCheckbox"/>-->
        <!--Страна-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="cityCheckbox"/>-->
        <!--Город-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="museumCheckbox"/>-->
        <!--Музей-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="archAttractionCheckbox"/>-->
        <!--Архитектура-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="naturalAttractionCheckbox"/>-->
        <!--Природа-->
        <!--</td>-->

        <!--</tr>-->
        <!--<tr class="checkbox">-->
        <!--<td>-->
        <!--<input type="checkbox" name="cafeCheckbox"/>-->
        <!--Кафе-->
        <!--</td>-->

        <!--<td colspan="2">-->
        <!--<input type="checkbox" name="entertainmentCheckbox"/>-->
        <!--Развлечения-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="shoppingCheckbox"/>-->
        <!--Шопинг-->
        <!--</td>-->
        <!--<td>-->
        <!--<input type="checkbox" name="hotelCheckbox"/>-->
        <!--Отель-->
        <!--</td>-->
        <!--</tr>-->
        <!--<tr>-->
        <!--<td class="b-search__button">-->
        <!--<input size="10" class="b-search__submit" type="submit"-->
        <!--tabindex="2"-->
        <!--value="Найти"/>-->
        <!--</td>-->
        <!--</tr>-->

        <!--</table>-->
        <!--</form>-->

        <!--</td>-->
    </xsl:template>

</xsl:stylesheet>
