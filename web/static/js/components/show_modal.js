/**
 * Created by romanscher on 4/29/17.
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