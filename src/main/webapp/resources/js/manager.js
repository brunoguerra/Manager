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
//Hide given object after given timeout
function hideAlert() {
    alert('test');
}
