const reveals = document.querySelectorAll('.reveal');
const io = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if(entry.isIntersecting){ entry.target.classList.add('show'); io.unobserve(entry.target); }
    });
}, { threshold: .15 });
reveals.forEach(el => io.observe(el));


function animateCount(el, end, duration=1200){
    const start = 0;
    const startTime = performance.now();
    function tick(now){
        const p = Math.min((now - startTime) / duration, 1);
        el.textContent = Math.floor(start + (end - start) * p).toLocaleString();
        if(p < 1) requestAnimationFrame(tick);
    }
    requestAnimationFrame(tick);
}
document.querySelectorAll('.num[data-count]').forEach(el=>{
    const observer = new IntersectionObserver(([e])=>{
        if(e.isIntersecting){ animateCount(el, parseInt(el.dataset.count,10)); observer.disconnect(); }
    }, { threshold: .6 });
    observer.observe(el);
});

document.getElementById("year").textContent = new Date().getFullYear();