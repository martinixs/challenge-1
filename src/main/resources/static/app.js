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

            success: function (SOAPResponse) {
                console.log(SOAPResponse.toXML());
                $('#response').text(dom2html(SOAPResponse.toXML()));
            },
            error: function (SOAPResponse) {
                $('#warning_message').text('Что-то пошло не так.. Проверте форму и поаторите запрос');
                $('#response').test('error');
                $('#dwn-btn').hide();
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

function dom2html(xmldom, tabcount) {
	var whitespace = /^\s+$/;
	var tabs = '  ';
	var xmlout = [];
	tabcount = (tabcount) ? tabcount : 0;

	xmlout.push('\n', repeat(tabs, tabcount), '<', xmldom.nodeName);
	for (var i in xmldom.attributes) {
		var attribute = xmldom.attributes[i];
		if (attribute.nodeType === 2) {
			xmlout.push(' ', attribute.name, '="', attribute.value, '"');
		}
	}
	xmlout.push('>');
	++tabcount;
	// for (var j in xmldom.childNodes) {
	for (var j = 0; j < xmldom.childNodes.length; j++) {
		var child = xmldom.childNodes[j];
		if (child.nodeType === 1) {
			xmlout.push(dom2html(child, tabcount));
		}
		if (child.nodeType === 3 && !whitespace.test(child.nodeValue)) {
			xmlout.push(child.nodeValue.trim());
		}
		if (child.nodeType === 4) {
			xmlout.push('<![CDATA[' + child.nodeValue + ']]>');
		}
	}
 	if (xmldom.childNodes.length === 1 && (xmldom.childNodes[0].nodeType === 3 || xmldom.childNodes[0].nodeType === 4)) {
		xmlout.push('</', xmldom.nodeName, '>');
	} else {
		xmlout.push('\n', repeat(tabs, --tabcount),'</', xmldom.nodeName, '>');
	}
	return xmlout.join('');
}
function repeat(x, n) {
	var s = '';
	for (;;) {
		if (n & 1) s += x;
		n >>= 1;
		if (n) x += x;
		else break;
	}
	return s;
}

