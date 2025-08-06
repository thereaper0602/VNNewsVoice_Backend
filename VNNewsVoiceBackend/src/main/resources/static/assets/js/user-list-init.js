function searchUsers(){
    const url = new URL(window.location.href);

    let searchQuery = document.getElementById("search-box").value;
    let role = document.getElementById("searchRole").value;

    if (searchQuery) {
        url.searchParams.set('username', searchQuery);
    } else {
        url.searchParams.delete('username');
    }

    if (role) {
        url.searchParams.set('role', role);
    } else {
        url.searchParams.delete('role');
    }


    url.searchParams.set('page', '1');

    window.location.href = url.toString();
}



document.addEventListener("DOMContentLoaded", function () {
    // Xử lý nút xóa người dùng
    const deleteButtons = document.querySelectorAll(".btn-delete-user");

    deleteButtons.forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-user-id");
            const form = document.getElementById(`deleteUserForm-${userId}`);

            Swal.fire({
                title: "Bạn có chắc chắn muốn xóa?",
                text: "Hành động này không thể hoàn tác!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d33",
                cancelButtonColor: "#3085d6",
                confirmButtonText: "Có, xóa người dùng!",
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {
                    form.submit();
                }
            });
        });
    });

    // Xử lý nút kích hoạt/vô hiệu hóa người dùng
    const toggleActiveButtons = document.querySelectorAll(".btn-toggle-active");

    toggleActiveButtons.forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-user-id");
            const isActive = this.getAttribute("data-is-active") === "true";
            const form = document.getElementById(`toggleActiveForm-${userId}`);
            
            const actionText = isActive ? "vô hiệu hóa" : "kích hoạt";
            
            Swal.fire({
                title: `Bạn có chắc chắn muốn ${actionText} người dùng này?`,
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#6c757d",
                confirmButtonText: `Có, ${actionText}!`,
                cancelButtonText: "Hủy"
            }).then((result) => {
                if (result.isConfirmed) {
                    form.submit();
                }
            });
        });
    });
});