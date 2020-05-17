$(document).ready(function() {

    var ID = function () {
      // Math.random should be unique because of its seeding algorithm.
      // Convert it to base 36 (numbers + letters), and grab the first 9 characters
      // after the decimal.
      return '_' + Math.random().toString(36).substr(2, 18);
    };

    var requestId = ID();

    // Start file download.
    document.getElementById("dwn_btn").addEventListener("click", function(){
        // Generate download of hello.txt file with some content
        var text = document.getElementById("response").value;
        var filename = "response.xml";

        download(filename, text);
    }, false);

    $('#soap').click(function(e) {
    	e.preventDefault();
    	 $('#warn_msg_txt').text("");
    	 $('#dwn_btn').hide();
    	 $('#response').text();

        var person  = {
            first_name: $('#first_name').val(),
            last_name: $('#last_name').val(),
            patr_name: $('#patr_name').val(),
            doc_number: $('#doc_number').val(),
            doc_series: $('#doc_series').val(),
            doc_issue_date: $('#doc_issue_date').val()
        };

        var ajaxReturn  = $.soap({
            namespaceQualifier: 'urn',
            url: 'http://localhost:8080/ws/',
            method: 'request',
            namespaceURL: 'urn:mFlow',

            data: {
                correctionId : requestId,
                payload : person
            },

            success: function (SOAPResponse) {

                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(SOAPResponse.toString(),"text/xml");
                var rCorrectionId = xmlDoc.getElementsByTagName("ns2:correctionId")[0].childNodes[0].nodeValue;
                var document = xmlDoc.getElementsByTagName("ns2:document")[0].childNodes[0].nodeValue;
                var status = xmlDoc.getElementsByTagName("ns2:status")[0].childNodes[0].nodeValue;
                console.log(requestId);
                console.log(rCorrectionId);
                console.log(status);

                if(status != 2) {
                    $('#warn_msg_txt').text("Повторите попытку позжe");
                } else {
                    if(requestId != rCorrectionId) {
                        $('#warn_msg_txt').text("Повторите попытку позжe");
                    } else {
                        $('#response').text($.base64.decode(document));
                        $('#dwn_btn').show();
                    }
                }
            },
            error: function (SOAPResponse) {
                $('#warning_message').text('Что-то пошло не так.. Проверте форму и поаторите запрос');
                $('#response').test('error');
                $('#dwn_btn').hide();
            },
            statusCode: {
            				404: function() {
            					console.log('404')
            				},
            				200: function() {
            					console.log('200')
            				}
            			}
        });
    });
});

function download(filename, text) {
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/xml;charset=utf-8,' + encodeURIComponent(text));
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
}

