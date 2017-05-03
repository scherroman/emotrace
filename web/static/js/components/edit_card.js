/**
 * Created by nashahzad on 5/1/17.
 */
$(document).ready(function(){
    //EDIT CHANNEL
   $(document).on('click', '.edit_channel', function(){
       debugger;
       var modal = $('#edit-channel-modal');
       var edit_channel_form = $('#edit-channel-form');
       var modal_title = edit_channel_form.data('form-title');

       modal.find('.modal-title').text(modal_title);

       modal.on('shown.bs.modal', function () {
           $(this).find('.focus-field').focus()
       });
       modal.on('hidden.bs.modal', function () {
           edit_channel_form.trigger("reset");
       });
       modal.modal('toggle');
   });
    $(document).on('click', '#edit-channel-submit', function() {
        debugger;
        var edit_channel_form = $('#edit-channel-form');
        var channels_div = $('#channels');

        $.ajax({
            url: edit_channel_form.attr('action'),
            data: $(edit_channel_form).serialize(),
            type: 'POST',
            success: function(response) {
                channels_div.prepend(response);
            },
            error: function(error) {
                console.log(error);
                alert(error)
            },
            complete: function(data) {
                $('#edit-channel-modal').modal('toggle');
            }
        });
    });
});