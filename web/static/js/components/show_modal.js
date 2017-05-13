/**
 * Created by romanscher on 4/29/17.
 *
 * A generalized, reusable handler for showing a modal on a page.
 *
 * Requires a button '.shows-modal' with a 'data-target' attribute set to the the modal's id.
 */
$(document).on('click', '.shows-modal', function() {
    var modal = $($(this).data('target'));
    var form = modal.find('form').first();
    var modal_title = form.data('form-title');

    modal.find('.modal-title').text(modal_title);

    modal.on('shown.bs.modal', function () {
        $(this).find('.focus-field').focus()
    });
    modal.on('hidden.bs.modal', function () {
        form.trigger("reset");
    });
    modal.modal('toggle');
});