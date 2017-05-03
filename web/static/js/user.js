/**
 * Created by romanscher on 4/19/17.
 */
$( document ).ready(function() {
    $.getScript("/static/js/components/load_more_cards.js", function(){});
    $.getScript("/static/js/components/delete_card.js", function(){});
    $.getScript("/static/js/components/show_modal.js", function(){});
    $(document).on('click', '#create-channel-submit', function() {
        var create_channel_form = $('#create-channel-form');
        var content = $('.loads-more-content');

        $.ajax({
            url: create_channel_form.attr('action'),
            data: $(create_channel_form).serialize(),
            type: 'POST',
            success: function(response) {
                content.prepend(response);
            },
            error: function(error) {
                console.log(error);
                alert(error.statusText)
            },
            complete: function(data) {
                $('#create-channel-modal').modal('toggle');
            }
        });
    });
    //EDIT MODAL WILL BE SHOWN
    $(document).on('click', '.edit_channel', function(){
        var modal = $('#edit-channel-modal');
        var body = modal.find('.modal-body');
        var channel_name = $(this).data('channel-name');
        var channel_description =$(this).data('channel-description');

        var delete_id = $(this).attr('id').split('-')[0];
        $('#channel_id').val(delete_id);

        modal.find('#name').val(channel_name);
        modal.find('#description').val(channel_description);
    });
    //EDIT MODAL SUBMIT HANDLER
    $(document).on('click', '#edit-channel-submit', function() {
        var edit_channel_form = $('#edit-channel-form');
        var channel_id = edit_channel_form.find('input[name="id"]').val();
        var channel_card = $('#card-container-' + channel_id);

        $.ajax({
            url: edit_channel_form.attr('action'),
            data: $(edit_channel_form).serialize(),
            type: 'POST',
            success: function(data){
                channel_card.replaceWith(data);
            },
            error: function(error) {
                console.log(error);
                alert(error);
            },
            complete: function(data) {
                $('#edit-channel-modal').modal('toggle');
            }
        });
    });
});
