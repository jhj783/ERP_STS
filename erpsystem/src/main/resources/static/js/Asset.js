document.addEventListener('DOMContentLoaded', function () {
    // 자산 데이터 가져오기
    fetch('/api/rest/asset')
        .then(response => response.json())
        .then(data => {
            const assetTableBody = document.querySelector('#assetTable tbody');
            let totalAssetAmount = 0;

            data.forEach(asset => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${asset.id}</td>
                    <td>${asset.name}</td>
                    <td>${parseFloat(asset.amount).toLocaleString()}원</td>
                    <td>${parseFloat(asset.acquisitionCost).toLocaleString()}원</td>
                    <td>${asset.type}</td>
                    <td>${asset.date}</td>
                `;
                assetTableBody.appendChild(row);
                totalAssetAmount += parseFloat(asset.amount);
            });

            document.getElementById('totalAssetAmount').innerText = totalAssetAmount.toLocaleString() + '원';
        })
        .catch(error => {
            console.error('자산 데이터 가져오기 오류:', error);
        });
});