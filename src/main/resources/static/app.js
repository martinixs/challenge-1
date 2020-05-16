$(document).ready(function() {

    var ID = function () {
      // Math.random should be unique because of its seeding algorithm.
      // Convert it to base 36 (numbers + letters), and grab the first 9 characters
      // after the decimal.
      return '_' + Math.random().toString(36).substr(2, 9);
    };

    $('#soap').click(function(e) {
        console.log('404');

    		// stop the form to be submitted...
    	e.preventDefault();

        var requestId = ID();

        var person  = {
            first_name: $('#first_name').val(),
            last_name: $('#last_name').val(),
            patr_name: $('#patr_name').val(),
            doc_number: $('#doc_number').val(),
            doc_series: $('#doc_series').val(),
            doc_issue_date: $('#doc_issue_date').val()
        };

        console.log(person)


        var ajaxReturn  = $.soap({
            namespaceQualifier: 'urn',
            url: 'http://localhost:8080/ws/',
            method: 'request',
            namespaceURL: 'urn:mFlow',

            data: {
                correctionId : requestId,
                payload : person
            },



//            beforeSend: function(SOAPEnvelope) {
//            				var xmlout = dom2html($.parseXML(SOAPEnvelope.toString()).firstChild);
//            				$('#requestXML').text(xmlout);
//            },

            success: function (SOAPResponse) {
                // do stuff with soapResponse
                // if you want to have the response as JSON use soapResponse.toJSON();
                // or soapResponse.toString() to get XML string
                // or soapResponse.toXML() to get XML DOM
            },
            error: function (SOAPResponse) {
                // show error
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

