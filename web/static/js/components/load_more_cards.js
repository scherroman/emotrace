/**
 * Created by romanscher on 4/20/17.
 */
$( document ).ready(function() {
    // Load More
    $(document).on('click', '#load_more', function() {
        var load_more_button = $(this);
        var content = $('.loads-more-content');
        var url = $(this).data('url');
        url += "?offset=" + content.find(".card").length;

        $.ajax({
            url: url,
            type: 'GET',
            success: function(response) {
                content.append(response);

                if (!$.trim(response)) {
                    // Disable load more button if no more content
                    load_more_button.wrap("<fieldset disabled></fieldset>");
                }
            },
            error: function(error) {
                console.log(error);
                alert(error.statusText)
            },
            complete: function(data) {}
        });
    });
});

