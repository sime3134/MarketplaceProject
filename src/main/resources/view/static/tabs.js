document.addEventListener("DOMContentLoaded", function () {
    handleTabs();
});

function handleTabs() {
    // Tab switching
    const tabs = document.querySelectorAll(".tab");
    const tabContents = document.querySelectorAll(".tab-content");

    tabs.forEach((tab) => {
      tab.addEventListener("click", (event) => {
        const targetTab = event.target.dataset.tab;

        // Remove active class from all tabs
        tabs.forEach((t) => {
          t.classList.remove("active");
        });

        // Add active class to the clicked tab
        event.target.classList.add("active");

        // Hide all tab contents
        tabContents.forEach((content) => {
          content.classList.remove("active");
        });

        // Show the tab content associated with the clicked tab
        document.getElementById(targetTab).classList.add("active");
      });
  });
}