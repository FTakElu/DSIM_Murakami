// CloudFlare Worker para proxy CORS
// Deploy em: https://workers.cloudflare.com/

addEventListener('fetch', event => {
  event.respondWith(handleRequest(event.request))
})

async function handleRequest(request) {
  // Permitir apenas requests do Amplify
  const allowedOrigins = [
    'https://main.dd3d0c3znbvkh.amplifyapp.com',
    'http://localhost:3000', // Para testes locais
    'http://127.0.0.1:3000'
  ];
  
  const origin = request.headers.get('Origin');
  
  // Responder a OPTIONS (preflight)
  if (request.method === 'OPTIONS') {
    return new Response(null, {
      status: 200,
      headers: {
        'Access-Control-Allow-Origin': origin && allowedOrigins.includes(origin) ? origin : allowedOrigins[0],
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type, X-Usuario-Email, Authorization',
        'Access-Control-Allow-Credentials': 'true',
        'Access-Control-Max-Age': '86400'
      }
    });
  }
  
  // Pegar URL do parâmetro
  const url = new URL(request.url);
  const targetUrl = url.searchParams.get('url');
  
  if (!targetUrl) {
    return new Response('URL parameter required', { 
      status: 400,
      headers: {
        'Access-Control-Allow-Origin': origin && allowedOrigins.includes(origin) ? origin : allowedOrigins[0],
      }
    });
  }
  
  try {
    // Fazer request para o backend EC2
    const response = await fetch(targetUrl, {
      method: request.method,
      headers: request.headers,
      body: request.body
    });
    
    // Copiar response
    const responseBody = await response.text();
    
    return new Response(responseBody, {
      status: response.status,
      headers: {
        ...response.headers,
        'Access-Control-Allow-Origin': origin && allowedOrigins.includes(origin) ? origin : allowedOrigins[0],
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type, X-Usuario-Email, Authorization',
        'Access-Control-Allow-Credentials': 'true'
      }
    });
    
  } catch (error) {
    return new Response(`Proxy error: ${error.message}`, { 
      status: 500,
      headers: {
        'Access-Control-Allow-Origin': origin && allowedOrigins.includes(origin) ? origin : allowedOrigins[0],
      }
    });
  }
}