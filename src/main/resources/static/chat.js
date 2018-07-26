$(function () {
    var username = "";
    debugger;
    $.ajax({
        type: "POST",
        url:"/userinfo",
        async: false,
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            username = result;
        },
        error: function () {
            alert("error");
        }
    });

    var websocket = new WebSocket("ws://localhost:8080/websocket/" + username);

    websocket.onmessage = function (ev) {
        onMessage(ev)
    };
    window.onbeforeunload = function () {
        closeWebSocket()
    };

    $("#send").on("click", sendMessage);
    $("#exit").on("click", exitRoom);

    $(document).keypress(function (e) {
        if (e.which === 13) {
            e.preventDefault();
            $("#send").click();
        }
    });

    function exitRoom() {
        websocket.close();
        window.location.href="http://localhost:8080/login";
    }

    function sendMessage() {
        var $msg = $("#msg");
        var content = $msg.val();
        if (content === "") {
            return;
        }
        $msg.val("");
        websocket.send(content);
    }

    function closeWebSocket() {
        websocket.close();
    }

    function onMessage(event) {
        var data = JSON.parse(event.data);
        var userName = data.userName;
        var message = data.message;
        var response =
            "<div class='panel panel-default'>" +
            "<div class='panel-heading'>"+ userName +"</div>" +
            "<div class='panel-body'>" + message + "</div>" +
            "</div>";
        $("#message").append(response);
    }
});

