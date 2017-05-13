/**
 * Created by romanscher on 4/19/17.
 *
 * JS for controlling user pages
 */
$( document ).ready(function() {

    /* REUSABLE HANDLERS */

    // Load more channels handler
    $.getScript("/static/js/components/load_more_cards.js", function(){});

    // Delete channel handler
    $.getScript("/static/js/components/delete_card.js", function(){});

    // Show modal handler
    $.getScript("/static/js/components/show_modal.js", function(){});

    /* NONREUSABLE HANDLERS */

    // Create channel handler
    $(document).on('click', '#create-channel-submit', function() {
        var create_channel_form = $('#create-channel-form');
        var content = $('.loads-more-content');

        // Create the channel server-side,
        // Returns: A new rendered channel card to add to the page
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

    // Edit channel handler
    $(document).on('click', '#edit-channel-submit', function() {
        var edit_channel_form = $('#edit-channel-form');
        var channel_id = edit_channel_form.find('input[name="id"]').val();
        var channel_card = $('#card-container-' + channel_id);

        // Edit the channel server-side,
        // Returns: A new rendered channel card to replace the edited channel card with
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

    // Populates info for edit modal
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
});
