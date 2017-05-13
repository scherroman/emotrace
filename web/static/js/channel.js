/**
 * Created by romanscher on 4/29/17.
 *
 * JS for controlling channel pages
 */
$( document ).ready(function() {

    /* REUSABLE HANDLERS */

    // Load more videos handler
    $.getScript("/static/js/components/load_more_cards.js", function () {});

    // Delete video handler
    $.getScript("/static/js/components/delete_card.js", function () {});

    // Show modal handler
    $.getScript("/static/js/components/show_modal.js", function () {});

    /* NONREUSABLE HANDLERS */

    // Add video to channel handler
    $(document).on('click', '#add-video-submit', function() {
        var add_video_form = $('#add-video-form');
        var content = $('.loads-more-content');
        var url = add_video_form.find('input[name="url"]').val();

        // Parse the video id from nearly all types of YouTube urls.
        // Regex from http://stackoverflow.com/q/10591547/4155686
        var video_id = url.match(/(?:https?:\/{2})?(?:w{3}\.)?youtu(?:be)?\.(?:com|be)(?:\/watch\?v=|\/)([^\s&]+)/);
        video_id = video_id[1];
        if(video_id == null) {
            console.log("Invalid Youtube url: " + url);
            alert("That youtube url doesn't work. Try another!");
            return;
        }
        add_video_form.find('input[name="externalId"]').val(video_id);

        // Add the video to the channel server-side,
        // Returns: A new rendered video card to add to the page
        $.ajax({
            url: add_video_form.attr('action'),
            data: $(add_video_form).serialize(),
            type: 'POST',
            success: function(response) {
                content.prepend(response);
            },
            error: function(error) {
                console.log(error);
                alert(error.statusText)
            },
            complete: function(data) {
                $('#add-video-modal').modal('toggle');
            }
        });
    });
});