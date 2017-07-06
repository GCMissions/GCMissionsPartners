<?php
//header('Access-Control-Allow-Origin: http://www.baidu.com'); //Set up http://www.baidu.com to allow cross-domain access
//header('Access-Control-Allow-Headers: X-Requested-With,X_Requested_With'); //Set the allowed cross domain header
date_default_timezone_set("Asia/chongqing");
error_reporting(E_ERROR);
header("Content-Type: text/html; charset=utf-8");

$CONFIG = json_decode(preg_replace("/\/\*[\s\S]+?\*\//", "", file_get_contents("config.json")), true);
$action = $_GET['action'];

switch ($action) {
    case 'config':
        $result =  json_encode($CONFIG);
        break;

    /* upload picture */
    case 'uploadimage':
    /* Upload graffiti */
    case 'uploadscrawl':
    /* Upload video */
    case 'uploadvideo':
    /* Upload file */
    case 'uploadfile':
        $result = include("action_upload.php");
        break;

    /*list image */
    case 'listimage':
        $result = include("action_list.php");
        break;
    /* list file */
    case 'listfile':
        $result = include("action_list.php");
        break;

    /* catch remote file */
    case 'catchimage':
        $result = include("action_crawler.php");
        break;

    default:
        $result = json_encode(array(
            'state'=> 'Request address error'
        ));
        break;
}

/*output result */
if (isset($_GET["callback"])) {
    if (preg_match("/^[\w_]+$/", $_GET["callback"])) {
        echo htmlspecialchars($_GET["callback"]) . '(' . $result . ')';
    } else {
        echo json_encode(array(
            'state'=> 'callback parameters sre illegal'
        ));
    }
} else {
    echo $result;
}