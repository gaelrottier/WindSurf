var rechercheResults = "#rechercheResults";

function showData(value) {
    if (!value.lenght === 0 || value.trim()) {
        $.ajax({
            url: "ServletRecherche?recherche=" + value,
            type: "POST",
            async: false,
            success: function(data) {
                html = "<option selected></option>";

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

    var id = $(rechercheResults + " :selected").val();

    window.location.href = "ServletResultatRecherche?t=" + label + "&q=" + id;
}

$(document).ready(function() {
    $('#recherche').bind('keypress keyup keydown focus', function() {
        showData($('#recherche').val());
    });

    $('#recherche').bind('blur', function() {
        hideResults();
    });

    $('#rechercheResults').bind('change', function() {
        searchResultSelected();
    });

    $('#rechercheResults').bind('focus', function() {
        $('#rechercheResults').show();
    });

    $('#rechercheResults').bind('blur', function() {
        $('#rechercheResults').hide();
    });

});
function hideResults() {
    $('#rechercheResults').hide();
}