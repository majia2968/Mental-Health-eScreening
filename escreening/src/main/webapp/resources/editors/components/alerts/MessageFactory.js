(function () {
    'use strict';

    angular.module('Editors').factory('MessageFactory', function () {

        /* Array of messages to display to the user within a specific context */
        var messages = [];
        /* Array of messages to display in any context or globally */
        var flashMessages = [];

        /*
         Overwrites any existing message and adds a new message or flash message
         */
        function set(type, msg, flash, sticky) {
            if (flash) {
                flashMessages.length = 0;
                flashMessages.push({type: type, msg: msg, close: closeFlashMessage, sticky: sticky});
            } else {
                messages.length = 0;
                messages.push({type: type, msg: msg, close: close, sticky: sticky});
            }
        }

        /*
         Adds a new message to the messages or flashMessages array
         Params: type (string). Can be success, info, warning or danger.
         msg (string or array of strings)
         flash (boolean) Whether or not the message(s) should be added onto the flashMessages array
         */
        function add(type, msg, flash, sticky) {
            if (flash) {
                if (_.isArray(msg)) {
                    _.each(msg, function (message) {
                        flashMessages.push({type: type, msg: message, close: closeFlashMessage, sticky: sticky});
                    });
                } else {
                    flashMessages.push({type: type, msg: msg, close: closeFlashMessage, sticky: sticky});
                }

            } else {
                if (_.isArray(msg)) {
                    _.each(msg, function (message) {
                        messages.push({type: type, msg: message, close: close, sticky: sticky});
                    });
                } else {
                    messages.push({type: type, msg: msg, close: close, sticky: sticky});
                }
            }
        }

        /*
         Returns messages or flashMessages array
         Params: flash (boolean). Optional. Will return flashMessages if truthy
         */
        function get(flash) {
            return (flash) ? flashMessages : messages;
        }

        // Convenience method for adding a success message
        function success(msg, sticky) {
            messages.push({type: 'success', msg: msg, close: close, sticky: sticky});
        }

        // Convenience method for adding an info message
        function info(msg, sticky) {
            messages.push({type: 'info', msg: msg, close: close, sticky: sticky});
        }

        // Convenience method for adding a warning message */
        function warning(msg, sticky) {
            messages.push({type: 'warning', msg: msg, close: close, sticky: sticky});
        }

        // Convenience method for adding a error message */
        function error(msg, sticky) {
            messages.push({type: 'danger', msg: msg, close: close, sticky: sticky});
        }

        // Remove message from messages array
        function close(index) {
            messages.splice(index, 1);
        }

        // Remove message from flashMessages array
        function closeFlashMessage(index) {
            flashMessages.splice(index, 1);
        }

        /*
         Clears all messages or flashMessages
         Params: flash (boolean). Optional. Will
         */
        function clear(flash) {
            (flash) ? flashMessages.length = 0 : messages.length = 0;
        }

        // Empties all messages and flashMessages
        function empty() {
            resetMessages(messages);
            resetMessages(flashMessages);
        }

        // Remove message or reset sticky to false
        function resetMessages(msgs) {
            var index = msgs.length;
            while (index--) {
                (msgs[index].sticky) ? msgs[index].sticky = false : msgs.splice(index, 1);
            }
        }

        /*
         returns internal size of flash or sticky messages
         Params: flash (boolean). Optional. Will return flashmessages size else will return the size of messages
         */
        function isEmpty(flash) {
            return (flash) ? flashMessages.length === 0 : messages.length === 0;
        }

        return {
            add: add,
            get: get,
            set: set,
            success: success,
            info: info,
            warning: warning,
            error: error,
            close: close,
            clear: clear,
            empty: empty,
            isEmpty: isEmpty
        };

    });
})();
