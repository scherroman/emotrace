/**
 * Created by romanscher on 4/20/17.
 */
$( document ).ready(function() {
    // Delete
    $(document).on('click', '.delete-card', function() {
        var element_to_delete = $(this).closest('.card-container');
        var delete_id = $(this).attr('id').split('-')[0];
        var delete_form = $('#' + delete_id + '-delete-form');

        $.ajax({
            url: delete_form.attr('action'),
            data: $(delete_form).serialize(),
            type: 'POST',
            success: function(response) {
                element_to_delete.remove();
            },
            error: function(error) {
                console.log(error);
                alert(error.statusText)
            },
            complete: function(data) {}
        });
    });
});
