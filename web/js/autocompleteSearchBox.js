var rechercheResults = "#rechercheResults";

function showData(value) {
    if (!value.lenght === 0 || value.trim()) {
        $.ajax({
            url: "ServletRecherche?recherche=" + value,
            type: "POST",
            async: false,
            success: function(data) {
                html = "";

                data = JSON.parse(data);

                $.each(data, function(key, value) {
                    html += "<optgroup label='" + key + "'>";

                    $.each(value, function(k, v) {
                        html += "<option value=\"" + v["id"] + "\">" + v["nom"] + "</option>";
                    });
                    html += "</optgroup>";
                });
                html += "";
                $(rechercheResults).show();
                $(rechercheResults).html(html);
            }
        });
    }
}

function searchResultSelected() {
    var label = $(rechercheResults + " :selected").parent().attr('label');

    var id = $(rechercheResults).val();

    window.location.href = "ServletResultatRecherche?t=" + label + "&q=" + id;
}

$(document).ready(function() {
    $('#recherche').bind('keypress', function() {
        showData($('#recherche').val());
    });
});