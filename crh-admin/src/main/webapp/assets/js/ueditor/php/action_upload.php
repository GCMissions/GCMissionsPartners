<?php
/**
 * upload file and video
 * User: Jinqn
 * Date: 14-04-09
 * Time: 10:17 am
 */
include "Uploader.class.php";

/* upload configuration */
$base64 = "upload";
switch (htmlspecialchars($_GET['action'])) {
    case 'uploadimage':
        $config = array(
            "pathFormat" => $CONFIG['imagePathFormat'],
            "maxSize" => $CONFIG['imageMaxSize'],
            "allowFiles" => $CONFIG['imageAllowFiles']
        );
        $fieldName = $CONFIG['imageFieldName'];
        break;
    case 'uploadscrawl':
        $config = array(
            "pathFormat" => $CONFIG['scrawlPathFormat'],
            "maxSize" => $CONFIG['scrawlMaxSize'],
            "allowFiles" => $CONFIG['scrawlAllowFiles'],
            "oriName" => "scrawl.png"
        );
        $fieldName = $CONFIG['scrawlFieldName'];
        $base64 = "base64";
        break;
    case 'uploadvideo':
        $config = array(
            "pathFormat" => $CONFIG['videoPathFormat'],
            "maxSize" => $CONFIG['videoMaxSize'],
            "allowFiles" => $CONFIG['videoAllowFiles']
        );
        $fieldName = $CONFIG['videoFieldName'];
        break;
    case 'uploadfile':
    default:
        $config = array(
            "pathFormat" => $CONFIG['filePathFormat'],
            "maxSize" => $CONFIG['fileMaxSize'],
            "allowFiles" => $CONFIG['fileAllowFiles']
        );
        $fieldName = $CONFIG['fileFieldName'];
        break;
}

/* Generate an upload instance object and complete the upload*/
$up = new Uploader($fieldName, $config, $base64);

/**
 * Get the upload file corresponding to the various parameters, the array structure
 * array(
 *     "state" => "",          //status,if successdul,return "SUCCESS"
 *     "url" => "",            //return address
 *     "title" => "",          //new file name
 *     "original" => "",       //original file name
 *     "type" => ""            //file type 
 *     "size" => "",           //file size
 * )
 */

/* return data */
return json_encode($up->getFileInfo());
