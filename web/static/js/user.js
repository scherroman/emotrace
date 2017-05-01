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
});
