/*!
 * FileInput Chinese Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author kangqf <kangqingfei@gmail.com>
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 */
(function ($) {
    "use strict";

    $.fn.fileinputLocales['zh'] = {
    		FileSingle: 'file',
    		        FilePlural: 'multiple files',
    		        BrowseLabel: 'select & hellip;',
    		        RemoveLabel: 'remove',
    		        RemoveTitle: 'Clear selected file',
    		        CancelLabel: 'Cancel',
    		        CancelTitle: 'Cancel in progress',
    		        UploadLabel: 'upload',
    		        UploadTitle: 'upload selected file',
    		        MsgNo: 'no',
    		        MsgCancelled: 'Cancel',
    		        MsgzoomTitle: 'view details',
    		        MsgzoomModalHeading: 'detailed preview',
    		        MsgSizeTooLarge: 'file' {name} "(<b> {size} KB </ b>) exceeds the allowed size <b> {maxSize} KB </ b>
    		        MsgFilesTooLess: 'You must choose at least <b> {n} </ b> {files} to upload.
    		        MsgFilesTooMany: 'the number of uploaded files <b> ({n}) </ b> exceeds the maximum number of files <b> {m} </ b>
    		        MsgFileNotFound: 'file' {name} "not found! ',
    		        MsgFileSecured: 'security restrictions, in order to prevent reading the file' {name} '.',
    		        MsgFileNotReadable: 'file' {name} "unreadable.",
    		        MsgFilePreviewAborted: 'Cancel' preview of '{name}'. '
    		        MsgFilePreviewError: 'An error occurred while reading' {name} '.'
    		        MsgInvalidFileType: 'Incorrect type' {name} ". Only files of type" {types} "are supported.
    		        MsgInvalidFileExtension: 'Incorrect file extension' {name} '. Only file extensions for "{extensions}" are supported.
    		        MsgUploadAborted: 'The file was aborted'
    		        MsgValidationError: 'validation error',
    		        MsgLoading: 'load the {index} file {files} & hellip;',
    		        MsgProgress: 'Load the {index} file {files} - {name} - {percent}% complete.
    		        MsgSelected: '{n} {files} selected'
    		        MsgFoldersNotAllowed: 'only support drag and drop files! Skip {n} drag the folder.',
    		        MsgImageWidthSmall: 'width of the image file' {name} 'must be at least {size} pixels.',
    		        MsgImageHeightSmall: 'The height of the image file' {name} 'must be at least {size} pixels.',
    		        MsgImageWidthLarge: 'width of the image file' {name} "can not exceed {size} pixels.
    		        MsgImageHeightLarge: The height of the image file {name} "can not exceed the {size} pixel.
    		        MsgImageResizeError: 'Unable to get image size adjustment. ',
    		        MsgImageResizeException: 'error and adjust the image size. <Pre> {errors} </ pre> ',
    		        DropZoneTitle: '',
    		        FileActionSettings: {
    		            RemoveTitle: 'delete file',
    		            UploadTitle: 'upload file',
    		            IndicatorNewTitle: 'no upload',
    		            IndicatorSuccessTitle: 'Upload',
    		            IndicatorErrorTitle: 'upload error',
    		            IndicatorLoadingTitle: 'upload ...'
    		        }
    		    };
})(window.jQuery);
