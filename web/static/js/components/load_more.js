/**
 * Created by romanscher on 4/20/17.
 */
$( document ).ready(function() {
    // Load More
    offset = 1;
    $(document).on('click', '#load_more', function() {
        var load_more_button = $(this);
        var load_more_content = $('.load_more_content');
        var url = $(this).data('url');
        url += "?offset=" + offset;
        offset += 1;

        $.ajax({
            url: url,
            type: 'GET',
            success: function(response) {
                load_more_content.append(response);

                if (!$.trim(response)) {
                    // Disable load more button if no more content
                    load_more_button.wrap("<fieldset disabled></fieldset>");
                }
            },
            error: function(error) {
                console.log(error);
                alert(error)
            },
            complete: function(data) {}
        });
    });
});

