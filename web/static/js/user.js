/**
 * Created by romanscher on 4/19/17.
 */
$( document ).ready(function() {
    $(document).on('click', '.main-fab', function() {
        var modal = $('#create-channel-modal');
        var create_channel_form = $('#create-channel-form');
        var modal_title = create_channel_form.data('form-title');

        modal.find('.modal-title').text(modal_title);

        modal.on('shown.bs.modal', function () {
            $(this).find('.focus-field').focus()
        });
        modal.on('hidden.bs.modal', function () {
            create_channel_form.trigger("reset");
        });
        modal.modal('toggle');
    });
    $(document).on('click', '#create-channel-submit', function() {
        var create_channel_form = $('#create-channel-form');
        var channels_div = $('#channels');

        console.log("submit");

        //Submit comment post
        $.ajax({
            url: create_channel_form.attr('action'),
            data: $(create_channel_form).serialize(),
            type: 'POST',
            success: function(response) {
                var channel = response
                channels_div.append(channel)
            },
            error: function(error) {
                console.log(error);
                alert(error)
            },
            complete: function(data) {
                $('#create-channel-modal').modal('toggle');
            }
        });
    });
});
