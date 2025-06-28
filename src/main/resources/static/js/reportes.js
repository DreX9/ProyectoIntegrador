// Configurar gráfico de pastel
const pieCtx = document.getElementById('pieChart').getContext('2d');
const pieChart = new Chart(pieCtx, {
    type: 'pie',
    data: {
        labels: ['Pescado congelado', 'Mariscos', 'Algas', 'Pescado Fresco'],
        datasets: [{
            label: 'Ventas por Categoría',
            data: [35, 25, 20, 20],
            backgroundColor: [
                'rgba(54, 162, 235, 0.7)',
                'rgba(255, 99, 132, 0.7)',
                'rgba(255, 206, 86, 0.7)',
                'rgba(75, 192, 192, 0.7)'
            ],
            borderColor: [
                'rgba(54, 162, 235, 1)',
                'rgba(255, 99, 132, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        title: {
            display: true,
            text: 'Distribución de Ventas por Categoría'
        }
    }
});

// Configurar gráfico de barras
const barCtx = document.getElementById('barChart').getContext('2d');
const barChart = new Chart(barCtx, {
    type: 'bar',
    data: {
        labels: ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'],
        datasets: [{
            label: 'Ventas',
            data: [11, 19, 15, 22, 18],
            backgroundColor: 'rgba(54, 162, 235, 0.5)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true
            }
        },
        title: {
            display: true,
            text: 'Ventas por Día'
        }
    }
});

// Manejo de selector de períodos
const periodBtns = document.querySelectorAll('input[name="periodo"]');
periodBtns.forEach(btn => {
    btn.addEventListener('change', function () {
        periodBtns.forEach(b => {
            b.checked = false;
            b.nextElementSibling.classList.remove('active');
        });
        this.checked = true;
        this.nextElementSibling.classList.add('active');

        // Aquí se actualizarían los datos de los gráficos según el período seleccionado
        // updateChartData(this.id);
    });
});