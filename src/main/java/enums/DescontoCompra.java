package enums;

import java.util.List;


import entities.Compra;
import entities.Jogo;

public enum DescontoCompra {
    DESC_VINTE(0.2),
    DESC_DEZ(0.1);

    double pctDesconto;

    DescontoCompra(double pctDesconto) {
        this.pctDesconto = pctDesconto;
    }

    public double getPctDesconto() {
        return pctDesconto;
    }

    public static DescontoCompra descontoParaAplicar(Compra compra) {
       List<Jogo> jogos =  compra.getJogos(); 
       int qtdeLançamento = 0, qtdePremium = 0, qtdeRegular = 0, qtdePromocao = 0; 
       for(int i=0;i<jogos.size();i++){ 
            switch(jogos.get(i).getCategoria()){ 
                case LANCAMENTO: qtdeLançamento+=1; 
                    break;
                case PREMIUM: qtdePremium+=1; 
                    break; 
                case REGULAR: qtdeRegular+=1;
                    break; 
                case PROMOCAO: qtdePromocao+=1;
                    break;
            }   
       }

       if(qtdeLançamento>=2) 
            return DESC_VINTE; 
       else{ 
            if(qtdePremium>=2 && jogos.size()>2) 
                return DESC_VINTE; 
            else{ 
                if(qtdePremium>=3) 
                    return DESC_VINTE; 
                else{ 
                    if(qtdeRegular >= 3 && qtdePremium>=3 || qtdeRegular >= 3 && (qtdePremium>=2 && jogos.size()>2) || qtdeRegular >= 3 && qtdeLançamento>=2 )  
                        return DESC_VINTE; 
                    else{ 
                        if(qtdeRegular >=5) 
                            return DESC_VINTE; 
                        else{ 
                            if(qtdePremium >=2) 
                                return DESC_DEZ; 
                            else{ 
                                if (qtdeRegular>=4) 
                                    return DESC_DEZ;
                            }
                        }
                    }
                }
            } 
            return null;
       }

    }
}
