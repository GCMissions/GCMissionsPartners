/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
(function ($) {
    'use strict';

    $.fn.bootstrapTable.locales['zh-CN'] = {
        formatLoadingMessage: function () {
            return 'Trying to load data, please wait……';
        },
        formatRecordsPerPage: function (pageNumber) {
            return 'Each page shows ' + pageNumber + ' records';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return 'According to ' + pageFrom + ' to ' + pageTo + ' records,total of ' + totalRows + ' records';
        },
        formatSearch: function () {
            return 'Search';
        },
        formatNoMatches: function () {
            return 'No match is found';
        },
        formatPaginationSwitch: function () {
            return 'Show/hide the paging';
        },
        formatRefresh: function () {
            return 'Refresh';
        },
        formatToggle: function () {
            return 'Switch';
        },
        formatColumns: function () {
            return 'Column';
        }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

})(jQuery);