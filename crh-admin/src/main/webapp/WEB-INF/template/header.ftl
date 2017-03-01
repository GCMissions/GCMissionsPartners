<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
<title>${systemName}后台管理系统<#if siteTitle> - ${siteTitle}</#if></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit"> 
<link rel="shortcut icon" href="${uiBase}img/favicon.ico" /> 
<link type="image/x-icon" href="" rel="bookmark" />
<meta content="width=device-width,height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<#--<meta name="viewport" content="width=device-width, height=device-height, initial-scale=.7, minimum-scale=.5, maximum-scale=2.0, user-scalable=no">-->
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />

<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="${uiBase}vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${uiBase}vendor/font-awesome/css/font-awesome.min.css?v=4.4.0" >
<link rel="stylesheet" href="${uiBase}vendor/ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="${uiBase}css/main.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/skins/skin-blue.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/pages.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-dialog/css/bootstrap-dialog.min.css" >
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-select/css/bootstrap-select.min.css" >
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-datatimepicker/css/bootstrap-datetimepicker.min.css">
<#list headComponents as item>
    <#if item == 'mainPage'>	
     	<#assign mainPage = true >    
    </#if>
</#list>   
<#if mainPage == true >

<#else>
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-table/bootstrap-table.min.css">
</#if>
<!--[if lt IE 9]>
<script src="${uiBase}vendor/html5shiv.min.js"></script>
<script src="${uiBase}vendor/respond.min.js"></script>
<![endif]-->  