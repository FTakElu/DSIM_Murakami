/* ===== CORREÇÃO DE NAVEGAÇÃO ===== */
/* Corrige links relativos que se acumulam no Amplify */

// Função para normalizar links de navegação
function normalizeLinks() {
    // Detectar se estamos em uma subpasta
    const isInPages = window.location.pathname.includes('/pages/');
    const basePath = isInPages ? '' : 'pages/';
    const homeBasePath = isInPages ? '../' : '';
    
    // Corrigir todos os links href
    document.querySelectorAll('a[href]').forEach(link => {
        const href = link.getAttribute('href');
        
        // Ignorar links externos e âncoras
        if (href.startsWith('http') || href.startsWith('#') || href.startsWith('mailto:')) {
            return;
        }
        
        // Corrigir links específicos
        if (href === 'index.html' || href === '../index.html') {
            link.setAttribute('href', homeBasePath + 'index.html');
        } else if (href.includes('.html') && !href.startsWith('../')) {
            // Se é um link para página HTML e não tem ../
            if (href.includes('pages/')) {
                // Remove páginas duplicadas do caminho
                const cleanHref = href.replace(/pages\//g, '');
                link.setAttribute('href', basePath + cleanHref);
            } else {
                link.setAttribute('href', basePath + href);
            }
        }
    });
    
    // Corrigir window.location.href em JavaScript inline
    const scripts = document.querySelectorAll('script');
    scripts.forEach(script => {
        if (script.innerHTML.includes('window.location.href')) {
            let content = script.innerHTML;
            
            // Corrigir redirecionamentos comuns
            content = content.replace(/window\.location\.href\s*=\s*['"]([^'"]+\.html)['"]/g, (match, url) => {
                if (url === 'index.html') {
                    return `window.location.href = '${homeBasePath}index.html'`;
                } else if (url.includes('pages/')) {
                    const cleanUrl = url.replace(/pages\//g, '');
                    return `window.location.href = '${basePath}${cleanUrl}'`;
                } else {
                    return `window.location.href = '${basePath}${url}'`;
                }
            });
            
            script.innerHTML = content;
        }
    });
}

// Função específica para corrigir formulários
function fixFormSubmissions() {
    // Corrigir redirecionamentos após sucesso
    window.redirectToPage = function(page) {
        const isInPages = window.location.pathname.includes('/pages/');
        const basePath = isInPages ? '' : 'pages/';
        const homeBasePath = isInPages ? '../' : '';
        
        if (page === 'index.html') {
            window.location.href = homeBasePath + 'index.html';
        } else {
            window.location.href = basePath + page;
        }
    };
}

// Executar correções quando a página carregar
document.addEventListener('DOMContentLoaded', function() {
    normalizeLinks();
    fixFormSubmissions();
    
    // Re-executar após 1 segundo para capturar links adicionados dinamicamente
    setTimeout(normalizeLinks, 1000);
});

// Executar também no window.onload para garantir
window.addEventListener('load', normalizeLinks);