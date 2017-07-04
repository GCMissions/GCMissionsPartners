/**
 * ueditor's Complete configuration item
 * You can configure the properties of the entire editor here
 */
/**************************tips********************************
 * All annotated configuration items are UEditor defaults.
 * Modify the default configuration first make sure that the actual use of the parameter is fully understood.
 * There are two main changes, one is to cancel the note here, and then modify the corresponding parameters; the other is in the implementation of the editor when the corresponding parameters.
 * When upgrading the editor, you can use the old configuration file to replace the new version of the configuration file, do not worry about the old configuration file due to the lack of new features required parameters caused by the script error.
 **************************tips********************************/

(function () {

    /**
     * Editor resource file root path. What it means is: the editor to instantiate the page as the current path, point to the editor resource file (ie, dialog folder) path.
     * In view of the many students in the use of the editor when the various path problems, here is strongly recommended that you use the "relative to the root directory of the relative path" to configure.
     * "Relative to the root directory of the relative path" is the beginning of the slash like "/ myProject / ueditor /" such a path.
     * If there are multiple pages in the site that do not need to instantiate the editor and refer to the same UEditor, the URL here may not apply to the editor for each page.
     * Thus, UEditor provides a root path that can be configured separately for editors of different pages. Specifically, the following code is written at the top of the page where the editor needs to be instantiated. Of course, you need to make the URL here equal to the corresponding configuration.
     * window.UEDITOR_HOME_URL = "/xxxx/xxxx/";
     */
    var URL = window.UEDITOR_HOME_URL || getUEBasePath();

    /**
     * Configuration item body. Note that all configurations that involve paths here do not miss URL variables.
     */
    window.UEDITOR_CONFIG = {

        //Add a path to the editor instance, which can not be commented
        UEDITOR_HOME_URL: URL

        // The server unilaterally requests the interface path
        , serverUrl: URL + "jsp/controller.jsp"

        //All the function buttons and drop-down boxes on the toolbar can be selected in the new editor's instance when you need to redefine
        , toolbars: [[
            'fullscreen', 'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'help', 'drafts'
        ]]
        
    };

    function getUEBasePath(docUrl, confUrl) {

        return getBasePath(docUrl || self.document.URL || self.location.href, confUrl || getConfigFilePath());

    }

    function getConfigFilePath() {

        var configPath = document.getElementsByTagName('script');

        return configPath[ configPath.length - 1 ].src;

    }

    function getBasePath(docUrl, confUrl) {

        var basePath = confUrl;


        if (/^(\/|\\\\)/.test(confUrl)) {

            basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/, '');

        } else if (!/^[a-z]+:/i.test(confUrl)) {

            docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, '');

            basePath = docUrl + "" + confUrl;

        }

        return optimizationPath(basePath);

    }

    function optimizationPath(path) {

        var protocol = /^[a-z]+:\/\//.exec(path)[ 0 ],
            tmp = null,
            res = [];

        path = path.replace(protocol, "").split("?")[0].split("#")[0];

        path = path.replace(/\\/g, '/').split(/\//);

        path[ path.length - 1 ] = "";

        while (path.length) {

            if (( tmp = path.shift() ) === "..") {
                res.pop();
            } else if (tmp !== ".") {
                res.push(tmp);
            }

        }

        return protocol + res.join("/");

    }

    window.UE = {
        getUEBasePath: getUEBasePath
    };

})();
