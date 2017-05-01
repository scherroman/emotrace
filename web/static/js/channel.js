/**
 * Created by romanscher on 4/29/17.
 */
$( document ).ready(function() {
    $.getScript("/static/js/components/load_more_cards.js", function () {});
    $.getScript("/static/js/components/delete_card.js", function () {});
    $.getScript("/static/js/components/show_modal.js", function () {});
    $(document).on('click', '#add-video-submit', function() {
        var add_video_form = $('#add-video-form');
        var content = $('.loads-more-content');

        var url = add_video_form.find('input[name="url"]').val();

        if (url.indexOf("watch?v=") == -1) {
            alert("Invalid YouTube video url. Url should be of the form ...youtube.com/watch?v=VIDEO_ID");
            return;
        }

        var video_id = url.split("watch?v=")[1];
        add_video_form.find('input[name="externalId"]').val(video_id);

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