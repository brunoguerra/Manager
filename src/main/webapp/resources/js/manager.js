/**
 * Created by Arek on 02.02.14.
 */
//Function that extract data-id and pass it to modal window:
$(document).ready(function() {
    $('input').attr('autocomplete', 'off');
});

//Delete modal
$(document).ready(function() {
    $('.confirm-delete').on('click', function(e) {
        e.preventDefault();
        $('#delete-form').find('input[name=id]').val($(this).data('id'));
    });

    $('#delete-cancel').on('click', function(e) {
        e.preventDefault();
        $('#delete-form').find('#id').val('');
    });

    $('#delete-url').on('click', function(e) {
        e.preventDefault();
        $('#delete-form').submit();
    });

});

//State change modal
/*<![CDATA[*/
$(document).ready(function() {
    var id = '';
    $('.state-change').on('click', function(e) {
        e.preventDefault();
        id = $(this).data('id');
        $('#state-value').parent('div').removeClass('has-error');
        $('#state-value').parent('div').find('label').remove();
        $('#state-value').val('');
        $('#state-value').focus();
        $('#state-change-form').find('input[name=id]').val(id);
    });

    $('#state-change-url').on('click', function(e) {
        var errors = 0;
        if( $('#state-value').val().length == 0 ) {
            $('#state-value').parent('div').addClass('has-error');
            var text = /*[[#{field-required}]]*/ 'Pole jest wymagane';
            $('#state-value').parent('div').append("<label class='control-label'>" + text + "</label>");
            errors++;
        }

        if(errors == 0) {
            $.ajax({
                url: "/item/state/" + id + "/change",
                type: "POST",
                data: "value=" + $('#state-value').val(),
                success: function(result) {
                    $('#state_' + id).text(result);
                }
            });

            $('#state-change-modal').modal('hide');
        }
    });
});
/*]]>*/

//Clear all input fields for parent element
$(document).ready(function() {
    $('.btn-clear').on('click', function(e) {
        var form = $(this).parents('form:first');
        form.find('input[type=text]').each(function() {
            $(this).val('');
        });
    });
});
